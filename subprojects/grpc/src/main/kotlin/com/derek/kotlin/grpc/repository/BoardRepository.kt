package com.derek.kotlin.grpc.repository


import com.derek.kotlin.protocol.board.GetBoardResult
import com.derek.kotlin.protocol.board.SetBoardParams
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
class BoardRepository {

    fun getBoardList(): List<Board> {
        return BoardTable.selectAll().map {Board.wrapRow(it)}
    }
    fun getBoardById(boardId: Long): Board {

        return Board[boardId]
    }

    fun setBoard(setBoardParams: SetBoardParams): Board{
        return Board[BoardTable.insertAndGetId {
            it[this.title] = setBoardParams.title
            it[this.contents] = setBoardParams.contents
        }.value]
    }

    fun deleteBoard(boardId: Long): Int{
        return BoardTable.deleteWhere { BoardTable.id eq boardId }
    }
}
