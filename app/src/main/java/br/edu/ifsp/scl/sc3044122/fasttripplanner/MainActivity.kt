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
 * Preserva o estado dos campos na rotação via onSaveInstanceState.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var etDestino: EditText
    private lateinit var etDias: EditText
    private lateinit var etOrcamento: EditText
    private lateinit var btnAvancar: Button

    companion object {
        const val EXTRA_DESTINO    = "extra_destino"
        const val EXTRA_DIAS       = "extra_dias"
        const val EXTRA_ORCAMENTO  = "extra_orcamento"

        // Chaves para salvar o estado na rotação
        private const val KEY_DESTINO   = "key_destino"
        private const val KEY_DIAS      = "key_dias"
        private const val KEY_ORCAMENTO = "key_orcamento"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Vincula componentes do layout
        etDestino   = findViewById(R.id.etDestino)
        etDias      = findViewById(R.id.etDias)
        etOrcamento = findViewById(R.id.etOrcamento)
        btnAvancar  = findViewById(R.id.btnAvancar)

        // Restaura o estado salvo antes da rotação (se existir)
        savedInstanceState?.let {
            etDestino.setText(it.getString(KEY_DESTINO, ""))
            etDias.setText(it.getString(KEY_DIAS, ""))
            etOrcamento.setText(it.getString(KEY_ORCAMENTO, ""))
        }

        btnAvancar.setOnClickListener {
            validarEAvancar()
        }
    }

    /**
     * Salva o conteúdo dos campos antes da rotação de tela.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_DESTINO,   etDestino.text.toString())
        outState.putString(KEY_DIAS,      etDias.text.toString())
        outState.putString(KEY_ORCAMENTO, etOrcamento.text.toString())
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
}
