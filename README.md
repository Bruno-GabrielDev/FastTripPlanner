# ✈️ FastTripPlanner

Aplicativo Android para planejamento rápido de viagens, desenvolvido em Kotlin com Views (XML).

## 📋 Informações do Projeto

| Item | Detalhe |
|------|---------|
| Aluno | sc3044122 |
| Pacote | br.edu.ifsp.scl.bes.prdm.sc3044122.fasttripplanner |
| Min SDK | 26 (Android 8 / Oreo) |
| Linguagem | Kotlin |
| UI | XML + Views |

## ⚙️ Funcionalidades

O aplicativo possui **três telas (Activities)** com navegação via **Intents explícitas**:

### Tela 1 — Dados da Viagem (`MainActivity`)
- Entrada de destino, número de dias e orçamento diário
- Validação dos dados (campos preenchidos e valores positivos)
- Botão "Avançar" → envia os dados para a Tela 2

### Tela 2 — Opções da Viagem (`OpcoesActivity`)
- Seleção do tipo de hospedagem (Econômica / Conforto / Luxo)
- Seleção de serviços extras (Transporte / Alimentação / Passeios)
- Botões "Calcular" (avança para Tela 3) e "Voltar" (retorna para Tela 1)

### Tela 3 — Resumo da Viagem (`ResumoActivity`)
- Exibe todos os dados inseridos
- Calcula e exibe o valor total da viagem
- Botão "Reiniciar" → volta para a Tela 1 limpando os dados

## 🧮 Regras de Cálculo

```
custoBase = dias * orçamento

Multiplicador hospedagem:
  Econômica → 1.0
  Conforto  → 1.5
  Luxo      → 2.2

Extras:
  Transporte   → + R$ 300 (fixo)
  Alimentação  → + R$ 50/dia
  Passeios     → + R$ 120/dia

Total = (custoBase × multiplicador) + extras
```

## 🗂️ Estrutura

```
app/src/main/
├── java/.../fasttripplanner/
│   ├── MainActivity.kt      ← Tela 1: Dados da Viagem
│   ├── OpcoesActivity.kt    ← Tela 2: Opções da Viagem
│   └── ResumoActivity.kt    ← Tela 3: Resumo e Cálculo
├── res/layout/
│   ├── activity_main.xml
│   ├── activity_opcoes.xml
│   └── activity_resumo.xml
└── AndroidManifest.xml
```

## ▶️ Como Rodar

1. Abra o projeto no **Android Studio**
2. Selecione um emulador ou dispositivo físico
3. Clique em **Run ▶️**

## ✅ Requisitos atendidos

- [x] RF01: Inserir dados da viagem
- [x] RF02: Selecionar opções adicionais
- [x] RF03: Exibir resumo completo
- [x] RF04: Navegação via Intents explícitas
- [x] RF05: Cálculo do custo total
- [x] RNF01: Compatível com Android 8.0+
- [x] RNF02: Código organizado e comentado
- [x] RNF03: Boas práticas (companion object, validações)
- [x] RNF04: README incluído
