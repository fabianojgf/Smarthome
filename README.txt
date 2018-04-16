Esse projeto conta com as implementações de uma Smarthome empregando MQTT e Sockets como mecanismos de comunicação entre os componentes.

A pasta MQTT contém 3 diretórios, que contém 2 projetos Java e um Android:
- SmartHomeController (Projeto que representa o controle remoto que controla e recebe informações de todos os equipamentos da casa)
- SmartHomeEquipament (Projeto que representa os equipamentos simulados, dentre eles: Ar-condicionado, TV, Porta, Portão, Lâmpada e Sensor de Luz)
- MQTTLEDApp (Projeto que representa de que simulada de maneira real os equipamentos Lâmpada e Sensor de Luz)

Obs: O projeto que utilziada MQTT depende da execução de broker, como o Mosquitto. Aqui só está implementado o lado cliente.

A pasta Sockets contém 4 diretórios, que contém 3 projetos Java e um Android:
- SmartHomeGateway (Projeto que representa o Gateway que gerencia a conexão e a troca de mensagens entre os componentes e o controle da smarthome)
- SmartHomeController (Projeto que representa o controle remoto que controla e recebe informações de todos os equipamentos da casa)
- SmartHomeEquipament (Projeto que representa os equipamentos simulados, dentre eles: Ar-condicionado, TV, Porta, Portão, Lâmpada e Sensor de Luz)
- SocketLEDApp (Projeto que representa de que simulada de maneira real os equipamentos Lâmpada e Sensor de Luz)

Implementação: Fabiano Gadelha
