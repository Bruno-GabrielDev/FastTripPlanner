package br.edu.ifsp.scl.sc3044122.fasttripplanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Tela 2 - Opções da Viagem
 * Recebe os dados da Tela 1 e adiciona:
 *  - Tipo de hospedagem (econômica, conforto, luxo)
 *  - Serviços extras (transporte, alimentação, passeios)
 *  - Modo Econômico: força hospedagem econômica, remove passeios
 *    e aplica 15% de desconto no orçamento diário.
 */
class OpcoesActivity : AppCompatActivity() {

    private lateinit var rgHospedagem: RadioGroup
    private lateinit var cbTransporte: CheckBox
    private lateinit var cbAlimentacao: CheckBox
    private lateinit var cbPasseios: CheckBox
    private lateinit var switchEconomico: Switch
    private lateinit var btnCalcular: Button
    private lateinit var btnVoltar: Button

    // Dados recebidos da Tela 1
    private var destino = ""
    private var dias = 0
    private var orcamento = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_opcoes)

        // Recupera extras da Intent
        destino   = intent.getStringExtra(MainActivity.EXTRA_DESTINO) ?: ""
        dias      = intent.getIntExtra(MainActivity.EXTRA_DIAS, 0)
        orcamento = intent.getDoubleExtra(MainActivity.EXTRA_ORCAMENTO, 0.0)

        // Vincula componentes do layout
        rgHospedagem   = findViewById(R.id.rgHospedagem)
        cbTransporte   = findViewById(R.id.cbTransporte)
        cbAlimentacao  = findViewById(R.id.cbAlimentacao)
        cbPasseios     = findViewById(R.id.cbPasseios)
        switchEconomico = findViewById(R.id.switchEconomico)
        btnCalcular    = findViewById(R.id.btnCalcular)
        btnVoltar      = findViewById(R.id.btnVoltar)

        // Listener do Modo Econômico
        switchEconomico.setOnCheckedChangeListener { _, isChecked ->
            aplicarModoEconomico(isChecked)
        }

        btnCalcular.setOnClickListener { calcularEAvancar() }
        btnVoltar.setOnClickListener   { finish() }
    }

    /**
     * Ativa ou desativa o Modo Econômico.
     * Quando ativo:
     *  - Seleciona hospedagem Econômica automaticamente
     *  - Desativa e desmarca Passeios
     *  - Bloqueia a seleção de outra hospedagem
     *  - Aplica 15% de desconto no orçamento diário
     */
    private fun aplicarModoEconomico(ativo: Boolean) {
        if (ativo) {
            // Força hospedagem econômica
            rgHospedagem.check(R.id.rbEconomica)

            // Bloqueia seleção de outra hospedagem
            findViewById<android.widget.RadioButton>(R.id.rbConforto).isEnabled = false
            findViewById<android.widget.RadioButton>(R.id.rbLuxo).isEnabled = false

            // Desmarca e desativa passeios
            cbPasseios.isChecked = false
            cbPasseios.isEnabled = false

        } else {
            // Reativa seleção de hospedagem
            findViewById<android.widget.RadioButton>(R.id.rbConforto).isEnabled = true
            findViewById<android.widget.RadioButton>(R.id.rbLuxo).isEnabled = true

            // Reativa passeios
            cbPasseios.isEnabled = true
        }
    }

    /**
     * Valida a seleção de hospedagem e avança para a Tela 3 com todos os dados.
     * Se modo econômico ativo, aplica 15% de desconto no orçamento.
     */
    private fun calcularEAvancar() {
        val hospedagem = when (rgHospedagem.checkedRadioButtonId) {
            R.id.rbEconomica -> "Econômica"
            R.id.rbConforto  -> "Conforto"
            R.id.rbLuxo      -> "Luxo"
            else -> {
                Toast.makeText(this, "Selecione um tipo de hospedagem!", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Aplica 15% de desconto se modo econômico estiver ativo
        val orcamentoFinal = if (switchEconomico.isChecked) {
            orcamento * 0.85
        } else {
            orcamento
        }

        val intent = Intent(this, ResumoActivity::class.java).apply {
            putExtra(MainActivity.EXTRA_DESTINO, destino)
            putExtra(MainActivity.EXTRA_DIAS, dias)
            putExtra(MainActivity.EXTRA_ORCAMENTO, orcamentoFinal)
            putExtra(EXTRA_HOSPEDAGEM, hospedagem)
            putExtra(EXTRA_TRANSPORTE, cbTransporte.isChecked)
            putExtra(EXTRA_ALIMENTACAO, cbAlimentacao.isChecked)
            putExtra(EXTRA_PASSEIOS, cbPasseios.isChecked)
            putExtra(EXTRA_MODO_ECONOMICO, switchEconomico.isChecked)
        }
        startActivity(intent)
    }

    companion object {
        const val EXTRA_HOSPEDAGEM    = "extra_hospedagem"
        const val EXTRA_TRANSPORTE    = "extra_transporte"
        const val EXTRA_ALIMENTACAO   = "extra_alimentacao"
        const val EXTRA_PASSEIOS      = "extra_passeios"
        const val EXTRA_MODO_ECONOMICO = "extra_modo_economico"
    }
}