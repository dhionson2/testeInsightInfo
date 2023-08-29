<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="ISO-8859-1">
    <title>Marcações feitas</title>
    <link href="${pageContext.request.contextPath}/resources/bootstrap-5.1.3-dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/styles.css">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr@4.6.6/dist/flatpickr.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery3.7/code.jquery.com_jquery-3.7.0.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/bootstrap-5.1.3-dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/jquery.mask/1.14.16/jquery.mask.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr@4.6.6/dist/flatpickr.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/flatpickr@4.6.6/dist/plugins/confirmDate/confirmDate.min.js"></script>
    
<script>
    $(document).ready(function() {
        function mostrarModalExclusao(id) {
            $('#confirmarExclusaoBtn').data('id', id);
            $('#excluirModal').modal('show');
        }

        $('form[action="MarcacoesServlet"]').on('submit', function(event) {
            var entrada = $('#entrada').val();
            var saida = $('#saida').val();
            var data = $('#data').val();
            var isEditingModal = $('#editarModal').hasClass('show');

            if (!isEditingModal && (entrada === '' || saida === '' || data === '')) {
                event.preventDefault();
                $('#errorModal').modal('show');
            } else {
                if (entrada !== '') {
                    var parsedEntrada = flatpickr.parseDate(entrada, "H:i");
                }
                if (saida !== '') {
                    var parsedSaida = flatpickr.parseDate(saida, "H:i");
                }
                // Restante do código para enviar o formulário
            }
        });

        $('#limparCampos').click(function() {
            $('#entrada').val('');
            $('#saida').val('');
            $('#data').val('');
        });

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

        $('.editar-btn').click(function() {
            var id = $(this).data('id');
            var data = $(this).closest('tr').find('td:eq(0)').text();
            var entrada = $(this).closest('tr').find('td:eq(1)').text();
            var saida = $(this).closest('tr').find('td:eq(2)').text();

            $('#editarId').val(id);
            $('#editarData').val(data);
            $('#editarEntrada').val(entrada);
            $('#editarSaida').val(saida);
        });

        $('.excluir-btn').click(function() {
            var id = $(this).data('id');
            mostrarModalExclusao(id);
        });

        $('#confirmarExclusaoBtn').click(function() {
            var id = $(this).data('id');
            window.location.href = 'MarcacoesServlet?acao=excluir&id=' + id;
        });
    });
</script>

</head>
<body>
<jsp:include page="/publica/publica-nav.jsp" />

<div class="container">
    <h1>Marcações Feitas</h1>
    <div class="row">
        <div class="col-md-6">      	
            <form action="MarcacoesServlet" method="post">
                <div class="container">
                    <div class="row">
                 	  <div class="col-md-4">
						    <div class="mb-3 bgRoxoEscuro text-center">
						        <label for="data" class="form-label">Data</label>
						        <input type="hidden" name="acao" value="adicionar">
						        <input type="text" class="form-control input-focus" id="data" name="data" placeholder="DD/MM/YYYY">
						    </div>
						</div>
                        <div class="col-md-4">
                            <div class="mb-3 bgRoxoEscuro text-center">
                                <label for="entrada" class="form-label">Entrada</label>
                                <input type="hidden" name="acao" value="adicionar">
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
                    <button type="submit" class="btn btn-custom py-2 ml-2 mr-2">Adicionar Horário</button>
                    <button type="button" class="btn btn-custom-secondary py-2 ml-2 mr-2" id="limparCampos">Limpar Campos</button>
                </div>
            </form>
        </div>
       
        <div class="col-md-6">
	        <div class="text-center">
	        	<img class="ml-5  shadow-green" alt="" src="${pageContext.request.contextPath}/resources/imagens/undraw_Time_management_re_tk5w.png" style="width: 80%;height: 300px;border-radius: 130px;">
	        </div>
        </div>
     </div>
<div class="mt-3">
    <table class="table" style="color: white;">
        <thead class="bgRoxoEscuro">
		    <tr>
		        <th>Data</th>
		        <th>Entrada</th>
		        <th>Saída</th>
		        <th>Ações</th>
		    </tr>
		</thead>

        <tbody>
           <c:forEach var="marcacao" items="${marcacoes}">
			    <tr>
			        <td>${marcacao.data}</td>
			        <td>${marcacao.entrada}</td>
			        <td>${marcacao.saida}</td>
			        <td>
			            <!-- Botão Editar -->
			            <button class="btn btn-sm btn-primary editar-btn" data-id="${marcacao.id}" data-bs-toggle="modal" data-bs-target="#editarModal">Editar</button>
			            <!-- Botão Excluir -->
			            <button class="btn btn-sm btn-danger excluir-btn" data-id="${marcacao.id}" data-bs-toggle="modal" data-bs-target="#excluirModal">Excluir</button>
			        </td>
			    </tr>
			</c:forEach>

        </tbody>
    </table>
</div>

</div>
<div class="modal fade" id="errorModal" tabindex="-1" aria-labelledby="errorModalLabel" aria-hidden="true">
  <div class="modal-dialog">
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

<!-- <script>
    $('#errorModal').on('show.bs.modal', function (e) {
        $(this).removeData('bs.modal');
    });
</script> -->
<!-- Modal editar-->
<div class="modal fade" id="editarModal" tabindex="-1" aria-labelledby="editarModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editarModalLabel">Editar Marcação</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Fechar"></button>
            </div>
            <div class="modal-body">
			    <!-- Form for editing data -->
			    <form action="MarcacoesServlet" method="post">
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

</body>
</html>
