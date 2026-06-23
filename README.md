code-generator-intellij-plugin
==============================

A cursor like Toy Intellij Idea plugin which generates code using `Ollama qwen2.5-coder:1.5b model` and `Groq llama-3.3-70b-versatile model`.

## Getting Started

- Install Ollama from [here](https://ollama.com/download/mac)
- Install `qwen2.5-coder:1.5b model` using command
```
ollama pull qwen2.5-coder:1.5b
```

- Create an account and get a token from `Groq` following instructions from [here](https://console.groq.com/docs/quickstart)

## Run plugin in Developer Mode

- Run following gradle commands to open a `new Ide window` with the plugin installed.
```
./gradlew clean
./gradlew runIde
```

## Install plugin (not required if running in developer mode)

- Run following gradle command to build the plugin
```
./gradlew build
```
- Now go to `Intellij Idea` -> `Settings` -> `Plugins` -> `Install Plugin from disk`.
- Choose `jar file` from `build` -> `libs` -> `jar file`.

## Configure Settings

- Visit `Intellij Idea` -> `Settings` -> `Tools` -> `Codegen`. The page should look like this.
[!img](images/ollama.png)

- Change to Groq like [!this](images/groq.png)

## Use it

- Open a code file in intellij idea and press `tab` to generate code.

## Design

[!Architecture](images/architecture.png)
