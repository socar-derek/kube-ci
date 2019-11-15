package com.derek.kotlin.api.controller

import com.derek.kotlin.api.config.BoardRpcConfiguration
import com.derek.kotlin.api.dto.BoardRequest
import com.derek.kotlin.api.properties.BoardRpcProperties
import com.derek.kotlin.api.util.toMap
import com.derek.kotlin.protocol.board.BoardServiceGrpc
import com.derek.kotlin.protocol.board.GetBoardParams
import com.derek.kotlin.protocol.board.SetBoardParams
import org.apache.coyote.Response
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/board")
class BoardController(
    private val boardServiceStub: BoardServiceGrpc.BoardServiceBlockingStub,
    private val boardRpcProperties: BoardRpcProperties
) {
    @GetMapping("/list/{id}")
    fun getBoardById(@PathVariable id: Long): ResponseEntity<MutableMap<String, Any>> {
        val getBoardResult = boardServiceStub.getBoard(GetBoardParams.newBuilder().setId(id).build()).boardProto

        println(getBoardResult.contents)

//        val returnMap = HashMap<String,Any>()
//        returnMap.put("id",getBoardResult.id)
//        returnMap.put("title",getBoardResult.title)
//        returnMap.put("content",getBoardResult.contents)
//
//        return ResponseEntity.ok().body(returnMap)

        return ResponseEntity.ok().body(toMap(getBoardResult))
    }

    @GetMapping("/list")
    fun getBoardList(): ResponseEntity<List<MutableMap<String, Any>>> {
        val getBoardsResultList = boardServiceStub.getBoards(
            GetBoardParams.newBuilder().setId(1).build()
        ).boardProtoList

        val returnList = ArrayList<MutableMap<String, Any>>()
        getBoardsResultList.map {
            val returnMap = HashMap<String, Any>()
            returnMap["id"] = it.id
            returnMap["title"] = it.title
            returnMap["content"] = it.contents
            returnList.add(returnMap)
        }
        val al = ArrayList<Int>()
        al.add(1)

        var getBoardsResult =
            boardServiceStub.getBoards(
                GetBoardParams.newBuilder()
                    .setId(1)
                    .build()
            )
                .boardProtoList.map {
                toMap(it)
            }

        return ResponseEntity.ok().body(returnList)

    }

    @PostMapping
    fun insertBoard(@RequestBody @Validated boardRequest: BoardRequest): ResponseEntity<MutableMap<String, Any>> {

        val setBoardResult =
            boardServiceStub.setBoard(SetBoardParams.newBuilder().setTitle(boardRequest.title).setContents(boardRequest.contents).build())
                .boardProto
        return ResponseEntity.ok().body(toMap(setBoardResult))
    }
    @GetMapping
    fun test(): ResponseEntity<String>{
        return ResponseEntity.ok().body(boardRpcProperties.url)
    }

}