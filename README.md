# Challenge Softtek App

Aplicativo Android de acompanhamento de humor, avaliação de riscos psicossociais e recursos de apoio, desenvolvido em Kotlin com Jetpack Compose.

## 📝 Visão Geral

O app permite que colaboradores:
- Registrem seu humor diário.
- Acessem avaliações de riscos psicossociais.
- Consultem recursos de apoio emocional.
- Visualizem dados de acompanhamento emocional.
- Faça login anônimo via API.

O app se comunica com a API backend (`challengeSofttekAPI`) usando Retrofit e armazena o token e ID do usuário no DataStore.

## 📂 Estrutura

- `ui.screens.login` → Tela de login anônimo com loading e controle de estado.
- `ui.screens.humor` → Tela de diário de humor.
- `ui.screens.menu` → Menu principal do app.
- `data.remote.service` → Retrofit client configurado com AuthInterceptor.
- `data.local` → UserPreferences (DataStore).
- `data.repository` → Repositórios para comunicação com a API.

## ⚙️ Pré-requisitos

- Android Studio Bumblebee ou superior
- Emulador Android (ou dispositivo físico)
- SDK mínimo: 24 (Android 7.0)
- Kotlin >= 1.9
- Gradle >= 8.x

## 🚀 Instalação

1. Clone o repositório:
```bash
   git clone https://github.com/seuusuario/challengeSofttekApp.git
```
2. Abra no Android Studio.
3. Configure o emulador ou conecte um dispositivo.

## 🏗️ Build e APK Release

1. Compile e gere o APK:
    - Menu **Build > Build Bundle(s) / APK(s) > Build APK(s)**
2. O APK ficará em `app/build/outputs/apk/release/app-release.apk`.
3. Instale no emulador ou dispositivo:
```bash
   adb install app-release.apk
```

## 🔄 Execução

- Abra o app no emulador ou dispositivo.
- Clique em **Entrar** para login anônimo.
- Navegue pelo menu principal.
- Registre humores e consulte informações.

## 📌 Notas

- O Retrofit client aponta para `http://10.0.2.2:8080/` para comunicação com backend rodando localmente no Docker.
- O app trata estados de loading, sucesso e erro de forma visual.
- Botão de logout fecha o app corretamente.
  