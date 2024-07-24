package com.horror.scarystory.Util

import androidx.compose.runtime.snapshots.SnapshotStateMap
import com.mongodb.client.MongoCollection
import org.litote.kmongo.updateOneById

object MongoDBUtil {

    /**
     * MongoDB 컬렉션에서 데이터를 Map으로 변환하여 반환합니다.
     *
     * @param T 컬렉션의 데이터 타입
     * @param selector 데이터에서 고유 ID를 추출하는 함수
     * @return 데이터의 고유 ID를 키로 하고 데이터를 값으로 가지는 Map
     */
    inline fun <reified T: Any> MongoCollection<T>.findDataToMap(noinline selector: (T) -> String): SnapshotStateMap<String, T> {
        return this.find().toList().associateBy { selector.toString() } as SnapshotStateMap<String, T>
    }

    /**
     * MongoDB 컬렉션에서 데이터를 List로 변환하여 반환합니다.
     *
     * @param T 컬렉션의 데이터 타입
     * @return 컬렉션의 모든 데이터를 포함하는 List
     */
    inline fun <reified T: Any> MongoCollection<T>.findDataToList(): List<T> {
        return this.find().toList()
    }

    /**
     * MongoDB 컬렉션에서 데이터를 ID를 기준으로 업데이트합니다.
     *
     * @param T 컬렉션의 데이터 타입
     * @param data 업데이트할 데이터
     * @param selector 데이터에서 고유 ID를 추출하는 함수
     * @throws IllegalStateException 데이터가 null인 경우 발생
     */
    inline fun <reified T: Any> MongoCollection<T>.updateById(data: T?, noinline selector: (T) -> String) {
        if (data == null) throw IllegalStateException("데이터가 정상적이지 않아 Update 를 진행할 수 없습니다.")
        this.updateOneById(selector, data)
    }

    /**
     * MongoDB 컬렉션에 데이터를 삽입합니다.
     *
     * @param T 컬렉션의 데이터 타입
     * @param data 삽입할 데이터
     * @throws IllegalStateException 데이터가 null인 경우 발생
     */
    inline fun <reified T: Any> MongoCollection<T>.insert(data: T?) {
        if (data == null) throw IllegalStateException("데이터가 정상적이지 않아 Insert 를 진행할 수 없습니다.")
        this.insertOne(data)
    }

}