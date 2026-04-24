package br.edu.ifsp.scl.sc3044122.fasttripplanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Tela 2 - Opções da Viagem
 * Recebe os dados da Tela 1 e adiciona:
 *  - Tipo de hospedagem (econômica, conforto, luxo)
 *  - Serviços extras (transporte, alimentação, passeios)
 * Envia tudo para a Tela 3 (ResumoActivity).
 */
class OpcoesActivity : AppCompatActivity() {

    private lateinit var rgHospedagem: RadioGroup
    private lateinit var cbTransporte: CheckBox
    private lateinit var cbAlimentacao: CheckBox
    private lateinit var cbPasseios: CheckBox
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
        rgHospedagem  = findViewById(R.id.rgHospedagem)
        cbTransporte  = findViewById(R.id.cbTransporte)
        cbAlimentacao = findViewById(R.id.cbAlimentacao)
        cbPasseios    = findViewById(R.id.cbPasseios)
        btnCalcular   = findViewById(R.id.btnCalcular)
        btnVoltar     = findViewById(R.id.btnVoltar)

        btnCalcular.setOnClickListener { calcularEAvancar() }
        btnVoltar.setOnClickListener   { finish() }  // volta para Tela 1
    }

    /**
     * Valida a seleção de hospedagem e avança para a Tela 3 com todos os dados.
     */
    private fun calcularEAvancar() {
        // Identifica hospedagem selecionada
        val hospedagem = when (rgHospedagem.checkedRadioButtonId) {
            R.id.rbEconomica -> "Econômica"
            R.id.rbConforto  -> "Conforto"
            R.id.rbLuxo      -> "Luxo"
            else -> {
                Toast.makeText(this, "Selecione um tipo de hospedagem!", Toast.LENGTH_SHORT).show()
                return
            }
        }

        // Monta Intent para a Tela 3
        val intent = Intent(this, ResumoActivity::class.java).apply {
            putExtra(MainActivity.EXTRA_DESTINO, destino)
            putExtra(MainActivity.EXTRA_DIAS, dias)
            putExtra(MainActivity.EXTRA_ORCAMENTO, orcamento)
            putExtra(EXTRA_HOSPEDAGEM, hospedagem)
            putExtra(EXTRA_TRANSPORTE, cbTransporte.isChecked)
            putExtra(EXTRA_ALIMENTACAO, cbAlimentacao.isChecked)
            putExtra(EXTRA_PASSEIOS, cbPasseios.isChecked)
        }
        startActivity(intent)
    }

    companion object {
        const val EXTRA_HOSPEDAGEM  = "extra_hospedagem"
        const val EXTRA_TRANSPORTE  = "extra_transporte"
        const val EXTRA_ALIMENTACAO = "extra_alimentacao"
        const val EXTRA_PASSEIOS    = "extra_passeios"
    }
}