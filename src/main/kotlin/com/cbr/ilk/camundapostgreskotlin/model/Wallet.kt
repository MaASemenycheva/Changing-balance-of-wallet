package com.cbr.ilk.camundapostgreskotlin.model

import java.time.LocalDateTime
import java.util.UUID


class Wallet {

    var origin: UUID? = null
    var name: String? = null
    var member_id: UUID? = null
    var type_id: Int? = null
    var status_id: Int? = null
    var balance: Int? = null


    constructor() {}
    constructor(origin: UUID?, name: String?, member_id: UUID?, type_id: Int?, status_id: Int?, balance: Int?) {
        this.origin = origin
        this.name = name
        this.member_id = member_id
        this.type_id = type_id
        this.status_id = status_id
        this.balance = balance
    }

    override fun toString(): String {
        return "Wallet{" +
                "origin=" + origin +
                ", name='" + name + '\'' +
                ", member_id=" + member_id +
                ", type_id=" + type_id +
                ", status_id=" + status_id +
                ", balance=" + balance +
                '}'
    }
}