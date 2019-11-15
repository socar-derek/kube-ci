package com.derek.kotlin.grpc.repository

import com.derek.kotlin.protocol.board.BoardProto
import com.derek.kotlin.protocol.board.GetBoardResult
import org.jetbrains.exposed.dao.*
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import java.util.*

object BoardTable: LongIdTable("tbl_board","b_id") {

    val title = varchar("b_title",50)
    val contents = varchar("b_contents",255)

}

class Board(id: EntityID<Long>): LongEntity(id){
    var title: String by BoardTable.title
    var contents: String by BoardTable.contents

    companion object : LongEntityClass<Board>(BoardTable)

    fun boardToBoardResponse(): GetBoardResult{
        return GetBoardResult.newBuilder().apply {this.boardProto = BoardProto.newBuilder().setTitle(title).setContents(contents).setId(id.value).build()}.build()
    }
    fun boardToBoardProto(): BoardProto{
//        return BoardProto.newBuilder().apply{
//            this.id = id
//            this.title = title
//            this.contents = contents
//        }.build()
        return BoardProto.newBuilder().setId(id.value).setContents(contents).setTitle(title).build();
    }
}
