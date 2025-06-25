# Backlog

<p align="justify">
Backlog é uma lista com prioridades dos requisitos ou funcionalidades do projeto que fornecem valor comercial ao cliente. Geralmente utilizado na técnica de desenvolvimento ágil, SCRUM. Os itens podem ser adicionados a esse registro em qualquer momento. Cabe ao gerente de produto avaliar o registro e atualizar as prioridades conforme requisitado.
</p>

O Backlog do produto deve ser:

* Detalhado o suficiente para que os desenvolvedores consigam entregar as funcionalidades ou histórias de usuário descritas.
* Estimável de forma a ser possível dizer quanto tempo levará para implementar a funcionalidade.
* Emergente de forma que possa ser continuamente atualizado.
* Priorizado de forma a trazer maior valor para o cliente.

<p align="justify">
Backlogs são focados em histórias de usuário, e contém sua descrição, identificação e priorização.
</p>

## Histórias de Usuário (User Stories)

<p align="justify">
Histórias de Usuário, como o próprio nome diz, são histórias focadas no usuário que descrevem de forma simples funcionalidades do produto de software. Por serem focadas no usuário, apresentam uma abordagem do que deve ser feito e não como. Devem ser detalhadas o suficiente para se poder derivar as tarefas necessárias para a implementação, e podem ter critérios de aceitação.
</p>

São escritas no formato:

**_Eu, como (quem?) quero (o quê?) para (por quê?)._**

## Backlog - PetShop

<style>
    #celula {
        vertical-align: middle;
        text-align: center;
        border: 0.5px solid rgba(0,0,0,0.2);
    }
</style>

<table>
    <thead>
        <tr>
            <th>Épico</th>
            <th>Feature</th>
            <th>ID</th>
            <th>História de Usuário</th>
            <th>Prioridade</th>
        </tr>
    </thead>
    <tbody >
        <tr>
            <td id="celula" rowspan="4">E01<br>Acesso e Autenticação</td>
            <td id="celula">F01<br>Cadastro</td>
            <td id="celula">US01</td>
            <td id="celula">Eu, como usuário quero cadastrar um email e senha para acessar o sistema.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula" rowspan="2">F02<br>Login</td>
            <td id="celula">US02</td>
            <td id="celula">Eu, como usuário quero logar usando o email e senha para acessar o sistema.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula">US03</td>
            <td id="celula">Eu, como usuário quero poder recuperar senha caso eu a esqueça para acessar o sistema.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula" rowspan="1">F03<br>Logout</td>
            <td id="celula">US04</td>
            <td id="celula">Eu, como usuário quero poder fazer logout para sair da conta.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula"rowspan="6">E02<br>Gerenciamento de Animais</td>
            <td id="celula"rowspan="3">F04<br>Cadastro de Animais</td>
            <td id="celula">US05</td>
            <td id="celula">Eu, como dono de pet quero cadastrar meu animal para acompanhar seus cuidados.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula">US06</td>
            <td id="celula">Eu, como dono de pet quero informar características do meu animal (peso, cor, tipo) para manter um registro completo.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula">US07</td>
            <td id="celula">Eu, como dono de pet quero informar se meu animal possui alguma doença para que os veterinários estejam cientes.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula"rowspan="3">F05<br>Edição de Animais</td>
            <td id="celula">US08</td>
            <td id="celula">Eu, como dono de pet quero poder atualizar o peso do meu animal para manter o registro atualizado.</td>
            <td id="celula">Média</td>
        </tr>
        <tr>
            <td id="celula">US09</td>
            <td id="celula">Eu, como dono de pet quero poder atualizar informações sobre doenças do meu animal para manter o histórico médico atualizado.</td>
            <td id="celula">Média</td>
        </tr>
        <tr>
            <td id="celula">US10</td>
            <td id="celula">Eu, como veterinário quero poder editar informações médicas de um animal para manter o prontuário atualizado.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula"rowspan="5">E03<br>Gerenciamento de Clientes</td>
            <td id="celula"rowspan="3">F06<br>Cadastro de Clientes</td>
            <td id="celula">US11</td>
            <td id="celula">Eu, como cliente quero me cadastrar no sistema para vincular meus pets.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula">US12</td>
            <td id="celula">Eu, como cliente quero cadastrar meu endereço completo para facilitar o atendimento.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula">US13</td>
            <td id="celula">Eu, como cliente quero vincular múltiplos animais ao meu cadastro para gerenciar todos os meus pets.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula"rowspan="2">F07<br>Edição de Clientes</td>
            <td id="celula">US14</td>
            <td id="celula">Eu, como cliente quero atualizar meus dados de contato para manter as informações atualizadas.</td>
            <td id="celula">Média</td>
        </tr>
        <tr>
            <td id="celula">US15</td>
            <td id="celula">Eu, como recepcionista quero poder buscar clientes por nome, CPF ou telefone para facilitar o atendimento.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula"rowspan="5">E04<br>Gerenciamento de Vacinas</td>
            <td id="celula"rowspan="3">F08<br>Registro de Vacinas</td>
            <td id="celula">US16</td>
            <td id="celula">Eu, como veterinário quero registrar vacinas aplicadas para manter o histórico de saúde do animal.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula">US17</td>
            <td id="celula">Eu, como veterinário quero registrar o lote e data de aplicação da vacina para controle de qualidade.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula">US18</td>
            <td id="celula">Eu, como veterinário quero registrar a data da próxima dose para garantir a continuidade do tratamento.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula"rowspan="2">F09<br>Histórico de Vacinas</td>
            <td id="celula">US19</td>
            <td id="celula">Eu, como dono de pet quero visualizar o histórico de vacinas do meu animal para acompanhar sua saúde.</td>
            <td id="celula">Média</td>
        </tr>
        <tr>
            <td id="celula">US20</td>
            <td id="celula">Eu, como veterinário quero visualizar o histórico completo de vacinação para tomar decisões médicas adequadas.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula"rowspan="6">E05<br>Agendamento e Consultas</td>
            <td id="celula"rowspan="3">F10<br>Agendamento</td>
            <td id="celula">US21</td>
            <td id="celula">Eu, como recepcionista quero agendar consultas para organizar o atendimento.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula">US22</td>
            <td id="celula">Eu, como cliente quero visualizar os horários disponíveis para agendar uma consulta para meu pet.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula">US23</td>
            <td id="celula">Eu, como cliente quero receber confirmação da consulta por email/SMS para não esquecer do compromisso.</td>
            <td id="celula">Média</td>
        </tr>
        <tr>
            <td id="celula"rowspan="3">F11<br>Atendimento</td>
            <td id="celula">US24</td>
            <td id="celula">Eu, como veterinário quero registrar o diagnóstico e tratamento para manter o histórico médico do animal.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula">US25</td>
            <td id="celula">Eu, como veterinário quero anexar exames e resultados ao prontuário para documentar o tratamento.</td>
            <td id="celula">Média</td>
        </tr>
        <tr>
            <td id="celula">US26</td>
            <td id="celula">Eu, como veterinário quero prescrever medicamentos com dosagem e período para orientar o tratamento do animal.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula"rowspan="4">E06<br>Relatórios e Dashboards</td>
            <td id="celula"rowspan="2">F12<br>Relatórios</td>
            <td id="celula">US27</td>
            <td id="celula">Eu, como gerente quero gerar relatórios de vacinas aplicadas para controle de estoque.</td>
            <td id="celula">Alta</td>
        </tr>
        <tr>
            <td id="celula">US28</td>
            <td id="celula">Eu, como gerente quero exportar relatórios em PDF e Excel para análise e apresentação.</td>
            <td id="celula">Média</td>
        </tr>
        <tr>
            <td id="celula"rowspan="2">F13<br>Dashboard</td>
            <td id="celula">US29</td>
            <td id="celula">Eu, como gerente quero visualizar indicadores de desempenho para acompanhar o negócio.</td>
            <td id="celula">Média</td>
        </tr>
        <tr>
            <td id="celula">US30</td>
            <td id="celula">Eu, como gerente quero visualizar gráficos de atendimentos por período para análise de demanda.</td>
            <td id="celula">Média</td>
        </tr>
    </tbody>
</table>



## Histórico de Revisões

Autor | Versão | Data(dd/mm/aaa) | Descrição
-|-|-|-
Yan Werlley | 1.0 | 10/05/2025 | Criação do backlog inicial com épicos, features e histórias de usuário

## Referências

* SCHWABER, Ken; SUTHERLAND, Jeff. **Guia do Scrum**. Disponível em: <https://scrumguides.org/docs/scrumguide/v2020/2020-Scrum-Guide-Portuguese-European.pdf>

* COHN, Mike. **User Stories Applied: For Agile Software Development**. Addison-Wesley Professional, 2004.
