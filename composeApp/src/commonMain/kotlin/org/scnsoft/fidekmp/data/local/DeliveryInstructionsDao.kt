package org.scnsoft.fidekmp.data.local

import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DeliveryInstructionDto
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.WineDto
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.CaseType
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DeliveryInstructionItem
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.DeliveryInstructionWine
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.Habillage
import org.scnsoft.fidekmp.data.api.deliveryinstructions.dto.ShippingInformation

@Dao
interface DeliveryInstructionsDao {

    // Insert a single DeliveryInstruction (without items, items are separate)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDeliveryInstruction(deliveryInstruction: DeliveryInstructionEntity): Long

    // Insert a list of items
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItems(items: List<ItemEntity>)

    // Transaction to insert a DeliveryInstruction and its items together
    @Transaction
    suspend fun insertDeliveryInstructionWithItems(instructionDto: DeliveryInstructionDto) {
        val instructionEntity = instructionDto.toEntity()
        insertDeliveryInstruction(instructionEntity)

        val itemEntities = instructionDto.items.map { itemDto ->
            itemDto.toEntity(instructionEntity.idUrl) // Pass parent's idUrl
        }
        if (itemEntities.isNotEmpty()) {
            insertItems(itemEntities)
        }
    }

    @Transaction
    @Query("SELECT * FROM delivery_instructions WHERE di_id_url = :idUrl")
    suspend fun getDeliveryInstructionWithItemsByIdUrl(idUrl: String): DeliveryInstructionWithItems?

    @Transaction
    @Query("SELECT * FROM delivery_instructions")
    suspend fun getAllDeliveryInstructionsWithItems(): List<DeliveryInstructionWithItems>

    @Query("DELETE FROM delivery_instructions")
    suspend fun clearAllDeliveryInstructions()

    @Query("DELETE FROM delivery_items")
    suspend fun clearAllItems()

    @Transaction
    suspend fun clearAllData() {
        clearAllItems() // Clear items first due to foreign key constraints
        clearAllDeliveryInstructions()
    }
}

// ------------- Mapper functions (DTO to Entity) -------------
// You can put these in a separate file or within the DAO's companion object or as extension functions

fun DeliveryInstructionDto.toEntity(): DeliveryInstructionEntity {
    return DeliveryInstructionEntity(
        idUrl = this.idUrl,
        type = this.type,
        digitalPassportTransfer = this.digitalPassportTransfer,
        shippingInformation = this.shippingInformation?.toEmbedded()
    )
}

fun ShippingInformation.toEmbedded(): ShippingInformationEmbedded {
    return ShippingInformationEmbedded(
        shippingIdUrl = this.idUrl,
        shippingId = this.id,
        typeOfDelivery = this.typeOfDelivery,
        deliveryFrom = this.deliveryFrom.toString(),
        deliveryTo = this.deliveryTo.toString(),
        addressCountry = this.addressCountry,
        addressLocality = this.addressLocality,
        addressRegion = this.addressRegion,
        postalCode = this.postalCode,
        streetAddress = this.streetAddress
    )
}

fun DeliveryInstructionItem.toEntity(parentDiIdUrl: String): ItemEntity {
    return ItemEntity(
        itemId = this.id,
        idUrl = this.idUrl,
        type = this.type,
        parentDeliveryInstructionIdUrl = parentDiIdUrl,
        wine = this.wine.toEmbedded(),
        caseType = this.caseType.toEmbedded(),
        habillage = this.habillage.toEmbedded(),
        casesQty = this.casesQty,
        pregnancyWarning = this.pregnancyWarning,
        regie = this.regie
    )
}

fun DeliveryInstructionWine.toEmbedded(): WineEmbedded {
    return WineEmbedded(
        idUrl = this.idUrl,
        wineId = this.id,
        wineType = this.wineType,
        name = this.name,
        imageUrl = this.imageUrl,
        country = this.country,
        region = this.region,
        appellation = this.appellation,
        classification = this.classification,
        color = this.color,
        agriculture = this.agriculture,
        producer = this.producer,
        champagneType = this.champagneType,
        grapeVarieties = this.grapeVarieties,
        alcohol = this.alcohol,
        certification = this.certification.toString(),
        sulfites = this.sulfites,
        allergens = this.allergens
    )
}

fun CaseType.toEmbedded(): CaseTypeEmbedded {
    return CaseTypeEmbedded(
        idUrl = this.idUrl,
        caseTypeId = this.id,
        nameEn = this.nameEn,
        nameFr = this.nameFr,
        // ... map all other CaseTypeDto fields ...
        materialNameEn = this.materialNameEn,
        materialNameFr = this.materialNameFr,
        bottlesQtyInCase = this.bottlesQtyInCase,
        bottleNameEn = this.bottleNameEn,
        bottleNameFr = this.bottleNameFr,
        totalAmountInLiters = this.totalAmountInLiters,
        bottleSizeLabelEn = this.bottleSizeLabelEn,
        bottleSizeLabelFr = this.bottleSizeLabelFr,
        litersPerCase = this.litersPerCase,
        unitEQVBottle = this.unitEqvbottle,
        totalEQVBottle = this.totalEqvbottle
    )
}

fun Habillage.toEmbedded(): HabillageEmbedded {
    return HabillageEmbedded(
        idUrl = this.idUrl,
        habillageId = this.id,
        name = this.name
    )
}
