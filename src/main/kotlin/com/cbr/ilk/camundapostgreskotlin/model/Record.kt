package com.cbr.ilk.camundapostgreskotlin.model

import java.time.LocalDateTime
import java.util.UUID


class Record {
    var message_uuid: UUID? = null
    var payload: String? = null
    var created: LocalDateTime? = null
    var processed: LocalDateTime? = null
    var message_type: Int? = null


    constructor() {}
    constructor(message_uuid: UUID?, payload: String?, created: LocalDateTime?, processed: LocalDateTime?, message_type: Int?) {
        this.message_uuid = message_uuid
        this.payload = payload
        this.created = created
        this.processed = processed
        this.message_type = message_type
    }

    override fun toString(): String {
        return "Record{" +
                "uuid=" + message_uuid +
                ", payload='" + payload + '\'' +
                ", created=" + created +
                ", processed=" + processed +
                ", message_type=" + message_type +
                '}'
    }
}