package br.edu.ifsp.scl.sc3044122.fasttripplanner

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

/**
 * Tela 1 - Dados da Viagem
 * Coleta destino, número de dias e orçamento diário.
 * Valida os dados e envia para a Tela 2 via Intent.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var etDestino: EditText
    private lateinit var etDias: EditText
    private lateinit var etOrcamento: EditText
    private lateinit var btnAvancar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincula componentes do layout
        etDestino   = findViewById(R.id.etDestino)
        etDias      = findViewById(R.id.etDias)
        etOrcamento = findViewById(R.id.etOrcamento)
        btnAvancar  = findViewById(R.id.btnAvancar)

        btnAvancar.setOnClickListener {
            validarEAvancar()
        }
    }

    /**
     * Valida os campos e, se válidos, navega para a Tela 2 (OpcoesActivity).
     */
    private fun validarEAvancar() {
        val destino   = etDestino.text.toString().trim()
        val diasStr   = etDias.text.toString().trim()
        val orcStr    = etOrcamento.text.toString().trim()

        // Validação de preenchimento
        if (destino.isEmpty() || diasStr.isEmpty() || orcStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show()
            return
        }

        // Conversão numérica com tratamento
        val dias = diasStr.toIntOrNull()
        val orcamento = orcStr.toDoubleOrNull()

        if (dias == null || dias <= 0) {
            Toast.makeText(this, "Número de dias inválido!", Toast.LENGTH_SHORT).show()
            return
        }

        if (orcamento == null || orcamento <= 0.0) {
            Toast.makeText(this, "Orçamento diário inválido!", Toast.LENGTH_SHORT).show()
            return
        }

        // Envia os dados para a próxima tela via Intent
        val intent = Intent(this, OpcoesActivity::class.java).apply {
            putExtra(EXTRA_DESTINO, destino)
            putExtra(EXTRA_DIAS, dias)
            putExtra(EXTRA_ORCAMENTO, orcamento)
        }
        startActivity(intent)
    }

    companion object {
        const val EXTRA_DESTINO    = "extra_destino"
        const val EXTRA_DIAS       = "extra_dias"
        const val EXTRA_ORCAMENTO  = "extra_orcamento"
    }
}