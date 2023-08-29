<nav class="navbar navbar-expand-lg navbar-dark" style="background-color: #2E2E47;"> <!-- Fundo roxo escuro -->
  <div class="container-fluid">
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" href="${pageContext.request.contextPath}/HorarioTrabalhoServlet?acao=horarioTrabalho" style="color: #1DFF9E;">Horário de Trabalho</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/MarcacoesServlet?acao=marcacao" style="color: #1DFF9E;">Marcações Feitas</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/CalcularAtrasoServlet?acao=calcular" style="color: #1DFF9E;">Cálculos de Atraso</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="${pageContext.request.contextPath}/CalcularHoraExtra?acao=hextra" style="color: #1DFF9E;">Cálculos de Hora Extra</a>
        </li>
      </ul>     
    </div>
  </div>
</nav>
