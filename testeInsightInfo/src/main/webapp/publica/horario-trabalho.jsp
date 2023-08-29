<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Horário de Trabalho</title>
  <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery3.7/code.jquery.com_jquery-3.7.0.min.js"></script>
  <link href="${pageContext.request.contextPath}/resources/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet" />
  <script type="text/javascript" src="${pageContext.request.contextPath}/resources/bootstrap-5.1.3-dist/js/bootstrap.min.js"></script>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr@4.6.6/dist/flatpickr.min.css">
  <script src="https://cdn.jsdelivr.net/npm/flatpickr@4.6.6/dist/flatpickr.min.js"></script>
  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/inputmask/5.0.6/jquery.inputmask.min.js"></script>
  

<script>
    $(document).ready(function() {
        // Função para exibir a modal de confirmação de exclusão
        function mostrarModalExclusao(id) {
            $('#confirmarExclusaoBtn').data('id', id);
            $('#excluirModal').modal('show');
        }

        // Adicione a validação antes de enviar o formulário
        $('form[action="HorarioTrabalhoServlet"]').on('submit', function(event) {
            var entrada = $('#entrada').val();
            var saida = $('#saida').val();
            var data = $('#data').val();
            var isEditingModal = $('#editarModal').hasClass('show'); // Verifica se a modal de edição está aberta

            if (!isEditingModal && (entrada === '' || saida === '' || data === '')) {
                event.preventDefault();
                $('#errorModal').modal('show');
            }
        });

        // Limpar campos quando o botão "Limpar Campos" for clicado
        $('#limparCampos').click(function() {
            $('#entrada').val('');
            $('#saida').val('');
            $('#data').val('');
        });

        // Inicialize os campos de entrada, saída e data com o flatpickr
        flatpickr("#entrada", {
            enableTime: true,
            noCalendar: true,
            dateFormat: "H:i",
        });

        flatpickr("#saida", {
            enableTime: true,
            noCalendar: true,
            dateFormat: "H:i",
        });

        flatpickr("#data", {
            enableTime: false,
            dateFormat: "d/m/Y",
        });

        $('#errorModal').on('show.bs.modal', function (e) {
            $(this).removeData('bs.modal');
        });

        // Captura o evento de clique no botão "Editar"
        $('.editar-btn').click(function() {
            var id = $(this).data('id');
            var data = $(this).closest('tr').find('td:eq(0)').text();
            var entrada = $(this).closest('tr').find('td:eq(1)').text();
            var saida = $(this).closest('tr').find('td:eq(2)').text();

            // Preenche os campos da modal com os dados
            $('#editarId').val(id);
            $('#editarData').val(data);
            $('#editarEntrada').val(entrada);
            $('#editarSaida').val(saida);
        });

        // Captura o evento de clique no botão "Excluir"
        $('.excluir-btn').click(function() {
            var id = $(this).data('id');
            mostrarModalExclusao(id);
        });

        // Captura o evento de clique no botão "Confirmar" na modal de exclusão
        $('#confirmarExclusaoBtn').click(function() {
            var id = $(this).data('id');
            window.location.href = 'HorarioTrabalhoServlet?acao=excluir&id=' + id;

        });
    });
</script>




</head>
<body>
  <jsp:include page="/publica/publica-nav.jsp" />
  <div class="container">
    <h1>Horário de Trabalho</h1>

    <div class="row">
      <div class="col-md-6">
        <form action="HorarioTrabalhoServlet" method="post">
          <div class="container">
		    <div class="row">
		        <div class="col-md-4">
				    <div class="mb-3 bgRoxoEscuro text-center">
				        <label for="data" class="form-label">Data</label>
				        <input type="text" class="form-control input-focus" id="data" name="data" placeholder="DD/MM/YYYY">
				    </div>			
		        </div>
		        <div class="col-md-4">
		            <div class="mb-3 bgRoxoEscuro text-center">
		                <label for="entrada" class="form-label">Entrada</label>
		                <input type="text" class="form-control input-focus" id="entrada" name="entrada" placeholder="HH:MM">
		            </div>
		        </div>
		        <div class="col-md-4">
		            <div class="mb-3 bgRoxoEscuro text-center">
		                <label for="saida" class="form-label">Saída</label>
		                <input type="text" class="form-control input-focus" id="saida" name="saida" placeholder="HH:MM">
		            </div>
		        </div>
		    </div>
		</div>
		          <div class="mt-4 text-center">
            <button type="submit" class="btn btn-custom py-2 ml-2 mr-2" name="acao" value="adicionar">Adicionar Horário</button>

            <button type="button" class="btn btn-custom-secondary py-2 ml-2 mr-2" id="limparCampos">Limpar Campos</button>
          </div>
        </form>
      </div>
      <div class="col-md-6">
        <div class="text-center">
          <img class="ml-5 shadow-green" alt="" src="${pageContext.request.contextPath}/resources/imagens/time.png" style="width: 89%;height: auto;border-radius: 130px;">
        </div>
      </div>
		<div class="mt-3">
		    <table class="table" style="color: white;">
		        <thead class="bgRoxoEscuro">
		            <tr class="">
		                <th>Data</th>
		                <th>Entrada</th>
		                <th>Saída</th>
		                <th>Ações</th> <!-- Nova coluna para as ações -->
		            </tr>
		        </thead>
				<tbody>
				    <c:forEach var="horario" items="${horarios}">
				        <tr>
				            <td>${horario.data}</td>
				            <td>${horario.entrada}</td>
				            <td>${horario.saida}</td>
				            <td>
				                <a href="#" class="btn btn-sm btn-primary editar-btn" data-id="${horario.id}" data-bs-toggle="modal" data-bs-target="#editarModal">Editar</a>
				                 <a href="#" class="btn btn-sm btn-danger excluir-btn" data-id="${horario.id}" data-bs-toggle="modal" data-bs-target="#excluirModal">Excluir</a>
				            </td>
				        </tr>
				    </c:forEach>
				</tbody>

		    </table>
		</div>


    </div>

    <div class="modal fade " id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel" aria-hidden="true">
      <div class="modal-dialog ">
        <div class="modal-content bgRoxo">
          <div class="modal-header">
            <h5 class="modal-title" id="errorModalLabel">Ops algo deu errado...</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
          </div>
          <div class="modal-body">
            Por favor, preencha todos os campos antes de enviar seu registro de ponto.
          </div>
          <div class="modal-footer justify-content-center">
            <button type="button" class="btn btn-custom" data-bs-dismiss="modal">Fechar</button>
          </div>
        </div>
      </div>
    </div>
    <!-- Modal para edição de horário -->
	<div class="modal fade" id="editarModal" tabindex="-1" aria-labelledby="editarModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="editarModalLabel">Editar Horário</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
	            </div>
	            <div class="modal-body">
	                <!-- Formulário para edição dos dados -->
		          <form action="HorarioTrabalhoServlet" method="post">
				    <input type="hidden" id="editarId" name="id">
				    <div class="mb-3">
				        <label for="editarData" class="form-label">Data</label>
				        <input type="text" class="form-control" id="editarData" name="data" placeholder="DD/MM/YYYY">
				    </div>
				    <div class="mb-3">
				        <label for="editarEntrada" class="form-label">Entrada</label>
				        <input type="text" class="form-control" id="editarEntrada" name="entrada" placeholder="HH:MM">
				    </div>
				    <div class="mb-3">
				        <label for="editarSaida" class="form-label">Saída</label>
				        <input type="text" class="form-control" id="editarSaida" name="saida" placeholder="HH:MM">
				    </div>
				    <button type="submit" class="btn btn-primary" name="acao" value="editar">Salvar</button>

				</form>

	            </div>
	        </div>
	    </div>
	</div>
	
	<!-- Modal excluir -->
	<div class="modal fade" id="excluirModal" tabindex="-1" aria-labelledby="excluirModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h5 class="modal-title" id="excluirModalLabel">Confirmar Exclusão</h5>
	                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
	            </div>
	            <div class="modal-body">
	                Tem certeza de que deseja excluir esta marcação de horário?
	            </div>
	           <div class="modal-footer">
				    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancelar</button>
				    <button type="button" class="btn btn-danger" id="confirmarExclusaoBtn" data-bs-dismiss="modal">Confirmar</button>
				</div>
        	</div>
	    </div>
	</div>
	
    
  </div>
</body>
</html>
