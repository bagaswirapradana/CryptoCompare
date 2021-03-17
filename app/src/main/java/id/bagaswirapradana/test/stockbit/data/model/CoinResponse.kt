package id.bagaswirapradana.test.stockbit.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by bagaswirapradana on 3/16/21.
 */
data class CoinResponse(
    @SerializedName("Message")
    @Expose
    val message: String,
    @SerializedName("Type")
    @Expose
    val type: Int,
    @SerializedName("Data")
    @Expose
    val data: ArrayList<CoinData>,
    @SerializedName("HasWarning")
    @Expose
    val hasWarning: Boolean
)

data class CoinData(
    @SerializedName("CoinInfo")
    @Expose
    val coinInfo: CoinInfo?,
    @SerializedName("DISPLAY")
    @Expose
    val display: Display?,
    @SerializedName("RAW")
    @Expose
    val raw: RAW?
)

data class Display(
    @SerializedName("IDR")
    @Expose
    val currency: CurrencyDisplay
)

data class RAW(
    @SerializedName("IDR")
    @Expose
    val currency: CurrencyRaw
)

data class CoinInfo(
    @SerializedName("Id")
    @Expose
    val id: String,
    @SerializedName("Name")
    @Expose
    val name: String,
    @SerializedName("FullName")
    val fullName: String,
    @SerializedName("Internal")
    val internal: String,
    @SerializedName("ImageUrl")
    val imageUrl: String,
    @SerializedName("Url")
    val url: String,
    @SerializedName("Algorithm")
    val algorithm: String,
    @SerializedName("ProofType")
    val proofType: String,
    @SerializedName("BlockNumber")
    val blockNumber: Int,
    @SerializedName("AssetLaunchDate")
    val assetLaunchDate: String,
    @SerializedName("Type")
    val type: Int,
    @SerializedName("DocumentType")
    val documentType: String
)

data class CurrencyRaw(
    @SerializedName("PRICE")
    @Expose
    val price: Double = 0.toDouble(),
    @SerializedName("CHANGE24HOUR")
    @Expose
    val change24Hour: Double = 0.toDouble(),
    @SerializedName("CHANGEPCT24HOUR")
    @Expose
    val changePct24Hour: Double = 0.toDouble()
)

data class CurrencyDisplay(
    @SerializedName("PRICE")
    @Expose
    val price: String,
    @SerializedName("CHANGE24HOUR")
    @Expose
    val change24Hour: String
)
