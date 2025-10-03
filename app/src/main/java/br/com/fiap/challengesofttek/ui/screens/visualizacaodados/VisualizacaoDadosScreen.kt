package br.com.fiap.challengesofttek.ui.screens.visualizacaodados

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.fiap.challengesofttek.data.remote.dto.HumorResponseDTO
import br.com.fiap.challengesofttek.data.remote.service.RetrofitClient
import br.com.fiap.challengesofttek.data.repository.HumorRepository
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.max
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizacaoDadosScreen(
    navController: NavController,
    viewModel: VisualizacaoDadosViewModel = viewModel(factory = object : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            val repository = HumorRepository(RetrofitClient.api)
            @Suppress("UNCHECKED_CAST")
            return VisualizacaoDadosViewModel(repository) as T
        }
    })
) {
    val humores by viewModel.humores.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Evolução Emocional", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = androidx.compose.material.icons.Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF329F6B))
            )
        },
        containerColor = Color(0xFFE8F5E9)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Acompanhamento dos níveis de humor gerais da empresa ao longo do tempo",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.fillMaxWidth(),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Spacer(Modifier.height(24.dp))

            if (humores == null) {
                // Carregando
                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                val list = humores!!
                if (list.isEmpty()) {
                    Text("Nenhum dado disponível", modifier = Modifier.fillMaxWidth(), textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                } else {
                    // Componente de gráfico customizado
                    MoodLineChart(
                        data = list,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }
            }

            Spacer(Modifier.height(24.dp))

            // Legenda
            Text(
                text = "Legenda:\n1 - Muito mal\n2 - Mal\n3 - Neutro\n4 - Bem\n5 - Muito bem",
                fontSize = 14.sp,
                color = Color.DarkGray
            )
        }
    }
}


/**
 * Composable que desenha o gráfico simples:
 * - eixo Y fixo (0..5) — mostra ticks 1..5 com texto.
 * - eixo X com datas (formato dd/MM) — pula rótulos se houver muitos pontos.
 * - linha conectando pontos e pontos individuais.
 */
@Composable
fun MoodLineChart(
    data: List<HumorResponseDTO>,
    modifier: Modifier = Modifier
) {
    // Convert to pairs (xIndex, yValue, label)
    val parsed = remember(data) {
        data.mapIndexedNotNull { idx, dto ->
            val nivel = dto.nivel ?: 0
            val label = formatDateForChart(dto.dataRegistro)
            Triple(idx, nivel.coerceIn(0, 5), label)
        }
    }

    // If no valid points, show placeholder
    if (parsed.isEmpty()) {
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
            Text("Sem dados para mostrar")
        }
        return
    }

    // Y axis range (we keep 0..5 but we draw ticks at 1..5)
    val yMin = 0f
    val yMax = 5f

    Canvas(modifier = modifier.background(Color.White)) {
        val fullWidth = size.width
        val fullHeight = size.height

        // margins
        val leftPadding = 56.dp.toPx() // espaço para labels Y
        val rightPadding = 16.dp.toPx()
        val topPadding = 16.dp.toPx()
        val bottomPadding = 48.dp.toPx() // espaço para X labels

        val chartWidth = fullWidth - leftPadding - rightPadding
        val chartHeight = fullHeight - topPadding - bottomPadding

        // Draw background grid horizontal lines for each y tick 1..5
        for (tick in 1..5) {
            val yVal = (tick - yMin) / (yMax - yMin)
            val y = topPadding + (1f - yVal) * chartHeight
            drawLine(
                color = Color(0xFFE0E0E0),
                start = Offset(x = leftPadding, y = y),
                end = Offset(x = leftPadding + chartWidth, y = y),
                strokeWidth = 1f
            )
        }

        // Draw Y-axis labels (1..5) with descriptions
        for (tick in 1..5) {
            val yVal = (tick - yMin) / (yMax - yMin)
            val y = topPadding + (1f - yVal) * chartHeight
            val label = tick.toString()
            // Draw text using drawContext.canvas.nativeCanvas
            drawContext.canvas.nativeCanvas.apply {
                val textPaint = android.graphics.Paint().apply {
                    color = android.graphics.Color.DKGRAY
                    textSize = 12.sp.toPx()
                    isAntiAlias = true
                }
                // alinhamento à direita
                val labelX = leftPadding - 8.dp.toPx()
                val baseline = y + (textPaint.textSize / 3f)
                drawText(label, labelX, baseline, textPaint)
            }
        }

        // Build point coordinates
        val pointPositions = parsed.map { (index, nivel, label) ->
            val xRatio = if (parsed.size == 1) 0.5f else index.toFloat() / (parsed.size - 1).toFloat()
            val x = leftPadding + xRatio * chartWidth
            val yRatio = (nivel - yMin) / (yMax - yMin)
            val y = topPadding + (1f - yRatio) * chartHeight
            Triple(Offset(x, y), nivel, label)
        }

        // Draw line path
        val linePath = Path().apply {
            pointPositions.forEachIndexed { i, pt ->
                val (offset, _, _) = pt
                if (i == 0) moveTo(offset.x, offset.y) else lineTo(offset.x, offset.y)
            }
        }

        drawPath(path = linePath, color = Color(0xFF329F6B), style = Stroke(width = 4f, cap = StrokeCap.Round))

        // Draw points
        pointPositions.forEach { (offset, _, _) ->
            drawCircle(color = Color.White, radius = 8f, center = offset)
            drawCircle(color = Color(0xFF329F6B), radius = 5f, center = offset)
        }

        // Draw X axis labels: attempt to show up to ~6 labels, else skip/pick evenly
        val maxLabels = 6
        val step = max(1, (pointPositions.size / maxLabels.toFloat()).roundToInt())
        val labelPaint = android.graphics.Paint().apply {
            color = android.graphics.Color.DKGRAY
            textSize = 12.sp.toPx()
            isAntiAlias = true
        }

        pointPositions.forEachIndexed { i, (_, _, label) ->
            if (i % step == 0 || i == pointPositions.lastIndex) {
                val x = pointPositions[i].first.x
                val y = topPadding + chartHeight + 16.dp.toPx()
                val text = label
                // Draw center-aligned text under the point
                val textWidth = labelPaint.measureText(text)
                drawContext.canvas.nativeCanvas.drawText(text, x - textWidth / 2f, y, labelPaint)
            }
        }

        // Optional small bounding box around chart
        drawRect(
            color = Color(0xFFEEEEEE),
            topLeft = Offset(leftPadding, topPadding),
            size = Size(chartWidth, chartHeight),
            style = Stroke(width = 1f),
            alpha = 0.3f
        )
    }
}

/** util: parse date string safe */
private fun parseDateSafe(dateStr: String?): LocalDateTime? {
    if (dateStr.isNullOrBlank()) return null
    return try {
        LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    } catch (e: Exception) {
        try {
            LocalDateTime.parse(dateStr, DateTimeFormatter.ISO_DATE_TIME)
        } catch (ex: Exception) {
            null
        }
    }
}

private val chartDateFormatter = DateTimeFormatter.ofPattern("dd/MM")

/** Retorna string formatada (dd/MM) para o gráfico */
private fun formatDateForChart(dateStr: String?): String {
    val date = parseDateSafe(dateStr)
    return date?.format(chartDateFormatter) ?: ""
}
