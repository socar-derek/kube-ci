package com.derek.kotlin.grpc.service


import com.derek.kotlin.grpc.repository.BoardRepository
import com.derek.kotlin.protocol.board.*
import io.grpc.Status
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class BoardService(private val boardRepository: BoardRepository) : BoardServiceGrpc.BoardServiceImplBase() {
    override fun getBoard(request: GetBoardParams, responseObserver: StreamObserver<GetBoardResult>) {
        val board = boardRepository.getBoardById(request.id)

        println(board.contents)
        try {
            val boardProto = board.boardToBoardProto()
            println(boardProto.contents)
            responseObserver.onNext(GetBoardResult.newBuilder().setBoardProto(boardProto).build())
        } catch (e: NoSuchElementException) {
            return responseObserver.onError(Status.INTERNAL.withDescription("Not found").asRuntimeException())
        }
        responseObserver.onCompleted()
    }

    override fun getBoards(request: GetBoardParams, responseObserver: StreamObserver<GetBoardsResult>) {
        val boardList = boardRepository.getBoardList()

        try {
            val boardProtoList = boardList.map { it.boardToBoardProto() }
            responseObserver.onNext(GetBoardsResult.newBuilder().apply { this.addAllBoardProto(boardProtoList) }.build())
        } catch (e: NoSuchElementException) {
            return responseObserver.onError(Status.INTERNAL.withDescription("Not found").asRuntimeException())
        }
        responseObserver.onCompleted()
    }

    override fun setBoard(request: SetBoardParams, responseObserver: StreamObserver<GetBoardResult>) {
        val board = boardRepository.setBoard(setBoardParams = request)

        try {
            val boardProto = board.boardToBoardProto()
            responseObserver.onNext(GetBoardResult.newBuilder().setBoardProto(boardProto).build())
        } catch (e: NoSuchElementException) {
            return responseObserver.onError(Status.INTERNAL.withDescription("Not found").asRuntimeException())
        }
        responseObserver.onCompleted()
    }
}