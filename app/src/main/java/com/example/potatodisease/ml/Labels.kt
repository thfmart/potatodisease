package com.example.potatodisease.ml

@Suppress("MagicNumber")
internal object Labels {
    val nameMap: HashMap<Int,String>
        get() {
            val map: HashMap<Int,String> = HashMap()
            map[0] = "Saudável"
            map[1] = "Pinta Preta"
            map[2] = "Requeima"
            map[3] = "Desconhecido"
            return map
        }
}