package com.github.samunohito.libs.io

import java.io.OutputStream
import java.nio.charset.Charset

/**
 * うんちをストリームする[OutputStream]の実装.
 * このクラスはジョークであり、ストリームに書き込まれるデータすべてが汚い排泄音に置き換えられてしまうため、
 * 業務アプリケーションなどのガチな用途では絶対に使用しないこと.
 */
class UnchiOutputStream : OutputStream {
    private val outputStream: OutputStream
    private val charset: Charset
    private var state: UnchiState

    /**
     * うんちをストリームする[OutputStream]のコンストラクタ.
     * 第一引数の[OutputStream]に対し、第二引数の[Charset]を用いてデータの書き込みを行う.
     *
     * @param outputStream ストリームしたうんちの文字コード.
     * @param charset うんちをストリームするための[OutputStream].
     */
    constructor(outputStream: OutputStream, charset: Charset) : super() {
        this.outputStream = outputStream
        this.charset = charset
        state = UnchiStateFactory.get(UnchiStateType.BU)
    }

    /**
     * うんちをストリームする[OutputStream]のコンストラクタ.
     * 第一引数の[OutputStream]に対し、[Charsets.UTF_8]を用いてデータの書き込みを行う.
     */
    constructor(outputStream: OutputStream) : this(outputStream, Charsets.UTF_8)

    /**
     * うんちをストリームする[OutputStream]のコンストラクタ.
     * 標準出力[System.out]に対し、第一引数の[Charset]を用いてデータの書き込みを行う.
     */
    constructor(charset: Charset) : this(System.out, charset)

    /**
     * うんちをストリームする[OutputStream]のコンストラクタ.
     * 標準出力[System.out]に対し、[Charsets.UTF_8]を用いてデータの書き込みを行う.
     */
    constructor() : this(System.out, Charsets.UTF_8)

    @Synchronized
    override fun write(value: Int) {
        writeInternal(state.generate(value))
        state = state.next(value)
    }

    override fun flush() {
        super.flush()
        outputStream.flush()
    }

    override fun close() {
        super.close()
        writeInternal("!!!!!!!!!!!!")
        outputStream.close()
    }

    private fun writeInternal(text: String) {
        outputStream.write(text.toByteArray(charset))
    }
}