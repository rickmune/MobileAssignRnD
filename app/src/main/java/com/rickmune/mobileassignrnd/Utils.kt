package com.rickmune.mobileassignrnd

import android.content.Context
import com.rickmune.mobileassignrnd.domain.City
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.coroutines.flow.flow


/**
 * Use of binary search to find the object that has a name that starts with the typed search term
 * Then use linear search to find matching objects to the left and right of the found object
 * Then merge the objects to the left with the found and those to the right
 */
suspend fun searchBinaryPlus(cities: List<City>, param: String) = flow {
    var low = 0
    var high = cities.size - 1
    val result = mutableListOf<City>()
    while (low < high) {
        val mid: Int = (low + high) / 2
        var name = cities[mid].name
        if (name.length > param.length) name = name.substring(0, param.length)
        val res = String.CASE_INSENSITIVE_ORDER.compare(name, param)
        if (res == 0) {
            val left = mutableListOf<City>()
            var l = mid
            while (l-- > low) {
                var namel = cities[l].name
                if (namel.length > param.length) namel = namel.substring(0, param.length)
                val add = String.CASE_INSENSITIVE_ORDER.compare(namel, param)
                if (add == 0){
                    left.add(cities[l])
                } else {
                    break
                }
            }
            val right = mutableListOf<City>()
            var r = mid
            while (r++ < high) {
                var namer = cities[r].name
                if (namer.length > param.length) namer = namer.substring(0, param.length)
                val add = String.CASE_INSENSITIVE_ORDER.compare(namer, param)
                if (add == 0){
                    right.add(cities[r])
                } else {
                    break
                }
            }
            for (n in left.size downTo 1){
                result.add(left[n-1])
            }
            result.add(cities[mid])
            right.forEach{city -> result.add(city)}
            break
        } else if (res > 0) {
            high = mid - 1
        } else {
            low = mid + 1
        }
    }
    emit(result)
}

suspend fun loadCities(cxt: Context, fileName: String) = flow {
    val inStream = cxt.assets.open(fileName)
    val buffer = ByteArray(inStream.available())
    inStream.read(buffer)
    inStream.close()
    val json = String(buffer, Charsets.UTF_8)
    val moshi = Moshi.Builder().build()
    val listType = Types.newParameterizedType(List::class.java, City::class.java)
    val jsonAdapter: JsonAdapter<List<City>> = moshi.adapter(listType)
    val cities = jsonAdapter.fromJson(json);
    emit(cities)
}