package br.edu.ifsp.scl.sc3044122.fasttripplanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale

/**
 * Tela 3 - Resumo da Viagem
 * Mostra todos os dados coletados e calcula o custo total.
 * Regras de cálculo:
 *   custoBase = dias * orçamento
 *   Multiplicador hospedagem: Econômica 1.0 | Conforto 1.5 | Luxo 2.2
 *   Extras: Transporte +R$300 | Alimentação +R$50/dia | Passeios +R$120/dia
 */
class ResumoActivity : AppCompatActivity() {

    private lateinit var tvResumo: TextView
    private lateinit var tvTotal: TextView
    private lateinit var btnReiniciar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resumo)

        tvResumo     = findViewById(R.id.tvResumo)
        tvTotal      = findViewById(R.id.tvTotal)
        btnReiniciar = findViewById(R.id.btnReiniciar)

        // Recupera dados das telas anteriores
        val destino     = intent.getStringExtra(MainActivity.EXTRA_DESTINO) ?: ""
        val dias        = intent.getIntExtra(MainActivity.EXTRA_DIAS, 0)
        val orcamento   = intent.getDoubleExtra(MainActivity.EXTRA_ORCAMENTO, 0.0)
        val hospedagem  = intent.getStringExtra(OpcoesActivity.EXTRA_HOSPEDAGEM) ?: ""
        val transporte  = intent.getBooleanExtra(OpcoesActivity.EXTRA_TRANSPORTE, false)
        val alimentacao = intent.getBooleanExtra(OpcoesActivity.EXTRA_ALIMENTACAO, false)
        val passeios    = intent.getBooleanExtra(OpcoesActivity.EXTRA_PASSEIOS, false)

        // Calcula o custo total
        val custoTotal = calcularCusto(dias, orcamento, hospedagem, transporte, alimentacao, passeios)

        // Monta texto de resumo
        val servicos = mutableListOf<String>().apply {
            if (transporte)  add("Transporte")
            if (alimentacao) add("Alimentação")
            if (passeios)    add("Passeios")
        }.joinToString(", ").ifEmpty { "Nenhum" }

        val resumo = """
            🌍 Destino: $destino
            📅 Dias: $dias
            💵 Orçamento diário: ${formatar(orcamento)}
            🏨 Hospedagem: $hospedagem
            🎒 Serviços: $servicos
        """.trimIndent()

        tvResumo.text = resumo
        tvTotal.text = "💰 Total da viagem: ${formatar(custoTotal)}"

        // Botão reiniciar - volta para a Tela 1 limpando a pilha
        btnReiniciar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    /**
     * Aplica as regras de cálculo definidas na especificação.
     */
    private fun calcularCusto(
        dias: Int,
        orcamento: Double,
        hospedagem: String,
        transporte: Boolean,
        alimentacao: Boolean,
        passeios: Boolean
    ): Double {
        // Base: dias * orçamento
        val custoBase = dias * orcamento

        // Multiplicador de hospedagem
        val multiplicador = when (hospedagem) {
            "Econômica" -> 1.0
            "Conforto"  -> 1.5
            "Luxo"      -> 2.2
            else        -> 1.0
        }

        var total = custoBase * multiplicador

        // Extras
        if (transporte)  total += 300.0
        if (alimentacao) total += 50.0 * dias
        if (passeios)    total += 120.0 * dias

        return total
    }

    /**
     * Formata valor em moeda brasileira (R$).
     */
    private fun formatar(valor: Double): String {
        return String.format(Locale("pt", "BR"), "R$ %.2f", valor)
    }
}
}