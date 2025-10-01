package br.com.fiap.challengesofttek.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.fiap.challengesofttek.domain.model.OpcaoResposta
import br.com.fiap.challengesofttek.domain.model.PerguntaAvaliacao

@Composable
fun PerguntaItem(
    pergunta: PerguntaAvaliacao,
    opcaoSelecionada: OpcaoResposta?,
    onSelect: (OpcaoResposta) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = pergunta.texto, fontSize = 16.sp, fontWeight = FontWeight.Medium)
        Spacer(Modifier.height(8.dp))
        Column(Modifier.selectableGroup()) {
            pergunta.opcoes.forEach { opcao ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = opcao == opcaoSelecionada,
                            onClick = { onSelect(opcao) },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 4.dp)
                ) {
                    RadioButton(
                        selected = opcao == opcaoSelecionada,
                        onClick = null // já tratado acima
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(text = opcao.texto)
                }
            }
        }
    }
}