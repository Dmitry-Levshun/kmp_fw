package org.scnsoft.fidekmp.data.local
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.Relation

// ------------- DeliveryInstruction Entity -------------
@Entity(
    tableName = "delivery_instructions",
    indices = [Index(value = ["di_id_url"], unique = true)] // Ensure idUrl is unique if it's a reliable unique identifier
)
data class DeliveryInstructionEntity(
    @PrimaryKey(autoGenerate = true) val internalId: Long = 0, // Auto-generated Room PK
    @ColumnInfo(name = "di_id_url") val idUrl: String, // From JSON "@id"
    val type: String?,
    val digitalPassportTransfer: String?,
    // ShippingInformation will be embedded
    @Embedded val shippingInformation: ShippingInformationEmbedded?
)

// ------------- ShippingInformation Embedded Object (for one-to-one with DeliveryInstruction) -------------
// No separate table, its fields are part of 'delivery_instructions' table
data class ShippingInformationEmbedded(
    @ColumnInfo(name = "si_id_url") val shippingIdUrl: String, // Prefix to avoid clashes if parent has 'idUrl'
    @ColumnInfo(name = "si_id") val shippingId: Int,           // Prefix to avoid clashes
    val typeOfDelivery: String?,
    val deliveryFrom: String?,
    val deliveryTo: String?,
    val addressCountry: String?,
    val addressLocality: String?,
    val addressRegion: String?,
    val postalCode: String?,
    val streetAddress: String?
    // deliveryInstruction IRI is implicitly the parent DeliveryInstructionEntity
)

// ------------- Item Entity (Many-to-One with DeliveryInstruction) -------------
@Entity(
    tableName = "delivery_items",
    foreignKeys = [
        ForeignKey(
            entity = DeliveryInstructionEntity::class,
            parentColumns = ["di_id_url"], // Link to the idUrl of DeliveryInstructionEntity
            childColumns = ["parent_di_id_url"],
            onDelete = ForeignKey.CASCADE // If DeliveryInstruction is deleted, its items are also deleted
        )
    ],
    indices = [
        Index(value = ["item_id_url"], unique = true),
        Index(value = ["parent_di_id_url"])
    ]
)
data class ItemEntity(
    @PrimaryKey @ColumnInfo(name = "item_id") val itemId: Int, // Using 'id' from JSON as PK
    @ColumnInfo(name = "item_id_url") val idUrl: String,
    val type: String?,
    @ColumnInfo(name = "parent_di_id_url") val parentDeliveryInstructionIdUrl: String, // Foreign Key

    // Wine, CaseType, Habillage will be embedded as they are 1-to-1 with Item
    @Embedded(prefix = "wine_") val wine: WineEmbedded,
    @Embedded(prefix = "casetype_") val caseType: CaseTypeEmbedded,
    @Embedded(prefix = "hab_") val habillage: HabillageEmbedded,

    val casesQty: Int,
    val pregnancyWarning: Boolean,
    val regie: String?
)

// ------------- Wine Embedded Object -------------
data class WineEmbedded(
    @ColumnInfo(name = "id_url") val idUrl: String,
    @ColumnInfo(name = "id") val wineId: Int, // Renamed to avoid clash with ItemEntity.itemId if not prefixed
    val wineType: String?,
    val name: String?,
    val imageUrl: String?,
    val country: String?,
    val region: String?,
    val appellation: String?,
    val classification: String?,
    val color: String?,
    val agriculture: String?,
    val producer: String?,
    val champagneType: String?,
    val grapeVarieties: String?,
    val alcohol: Double?,
    val certification: String?,
    val sulfites: Boolean?,
    val allergens: String?
)

// ------------- CaseType Embedded Object -------------
data class CaseTypeEmbedded(
    @ColumnInfo(name = "id_url") val idUrl: String,
    @ColumnInfo(name = "id") val caseTypeId: Int,
    val nameEn: String?,
    val nameFr: String?,
    val materialNameEn: String?,
    val materialNameFr: String?,
    val bottlesQtyInCase: Int?,
    val bottleNameEn: String?,
    val bottleNameFr: String?,
    val totalAmountInLiters: Double?,
    val bottleSizeLabelEn: String?,
    val bottleSizeLabelFr: String?,
    val litersPerCase: Double?,
    val unitEQVBottle: Double?,
    val totalEQVBottle: Int?
)

// ------------- Habillage Embedded Object -------------
data class HabillageEmbedded(
    @ColumnInfo(name = "id_url") val idUrl: String,
    @ColumnInfo(name = "id") val habillageId: Int,
    val name: String?
)


// ------------- For Querying with Relations (Optional but Recommended) -------------
// This class helps retrieve a DeliveryInstruction with its list of items.
data class DeliveryInstructionWithItems(
    @Embedded val deliveryInstruction: DeliveryInstructionEntity,
    @Relation(
        parentColumn = "di_id_url", // PK of DeliveryInstructionEntity (using idUrl here)
        entityColumn = "parent_di_id_url" // FK in ItemEntity
    )
    val items: List<ItemEntity>
)
