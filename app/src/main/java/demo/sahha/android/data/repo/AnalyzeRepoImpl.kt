package demo.sahha.android.data.repo

import demo.sahha.android.data.DemoCache
import demo.sahha.android.domain.model.dto.SahhaAnalysisDto
import demo.sahha.android.domain.model.dto.SahhaFactorDto
import demo.sahha.android.domain.repo.AnalyzeRepo
import org.json.JSONArray
import org.json.JSONObject
import sdk.sahha.android.source.Sahha
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AnalyzeRepoImpl @Inject constructor(

) : AnalyzeRepo {
    override suspend fun getAnalysis(id: String): SahhaAnalysisDto? {
        return DemoCache.analysis?.find { it.id == id }
    }
    override suspend fun getInferences(): List<SahhaAnalysisDto>? = suspendCoroutine { cont ->
        Sahha.analyze(
            dates = Pair(
                LocalDateTime.now().minusDays(7),
                LocalDateTime.now()
            )
        ) { _, successful ->
            successful?.also {
                val json = JSONObject(successful)
                val jsonArray = json.get("inferences") as JSONArray
                val analysis = mutableListOf<SahhaAnalysisDto>()
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    analysis.add(
                        SahhaAnalysisDto(
                            id = obj.getString("id"),
                            type = obj.getString("type"),
                            state = obj.getString("state"),
                            score = obj.getDouble("score"),
                            factors = let {
                                val factors = mutableListOf<SahhaFactorDto>()
                                val factorsJsonArray = obj.getJSONArray("factors")
                                for (j in 0 until factorsJsonArray.length()) {
                                    factors.add(
                                        SahhaFactorDto(
                                            name = factorsJsonArray.getJSONObject(j)
                                                .getString("name"),
                                            value = factorsJsonArray.getJSONObject(j)
                                                .getString("value")
                                        )
                                    )
                                }
                                factors
                            },
                            inputData = let {
                                val inputData = mutableListOf<String>()
                                val inputDataJsonArray = obj.getJSONArray("inputData")
                                for (k in 0 until inputDataJsonArray.length()) {
                                    inputData.add(inputDataJsonArray.getString(k))
                                }
                                inputData
                            },
                            createdAt = obj.getString("createdAt"),
                        )
                    )
                }
                DemoCache.analysis = analysis
                cont.resume(analysis)
            } ?: cont.resume(null)
        }
    }
}