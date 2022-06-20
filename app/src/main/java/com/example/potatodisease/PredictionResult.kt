package com.example.potatodisease


enum class PredictionResult(
    val idx: Int,
    val title: String,
    val description: String,
    val picture: Int
) {
    SAUDAVEL(0, "Saudável", "Descrição", -1),
    PINTA_PRETA(
        1,
        "Pinta Preta",
        "Ataca primeiramente as folhas mais velhas, onde causa lesões concêntricas, e pode provocar desfolha total das plantas, reduzindo o ciclo da cultura, resultando na produção de tubérculos pequenos e, consequentemente, em baixa produtividade.",
        R.drawable.pinta_preta
    ),
    REQUEIMA(
        2,
        "Requeima",
        "A requeima é causada por Phytophthora infestans, um oomiceto (antigamente classificado como fungo) que produz esporângios, zoósporos e oósporos, que são estruturas responsáveis pela dispersão e/ou sobrevivência do patógeno.",
        R.drawable.requeima
    ),
    DESCONHECIDO(3, "Desconhecido", "Doença desconhecida", R.drawable.ic_baseline_help_outline_24);

    companion object {
        fun findByIdx(idx: Int): PredictionResult {
            for (item in values()) {
                if (item.idx == idx) {
                    return item
                }
            }
            return DESCONHECIDO
        }
    }
}