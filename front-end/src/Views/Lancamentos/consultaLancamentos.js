import React from "react";
import { withRouter } from "react-router-dom";
import Card from "../../Components/card";
import FormGroup from "../../Components/form-group";
import SelectMenu from "../../Components/selectMenu";
import LancamentosTable from './lancamentosTable';
import LancamentoService from './../../app/service/lancamentoService';
import LocalStorageService from "../../app/service/localStorageService";

class ConsultaLancamentos extends React.Component {


    state = {
        ano: '',
        mes: '',
        tipo: '',
        lancamentos: []
    }

    constructor() {
      super();
      this.service = new LancamentoService();
    }

    buscar = () => {
      const usuarioLogado = LocalStorageService.obterItem('_usuario_logado')
      
      const lancamentoFiltro = {
        ano: this.state.ano,
        mes: this.state.mes,
        tipo: this.state.tipo,
        usuario: usuarioLogado.id
      }

      this.service
        .consultar(lancamentoFiltro)
        .then((response) => {
          this.setState({lancamentos: response.data})
        }).catch((error) => {
          console.log(error.response.data);
        })
      

      console.log(this.state);
    }
    
    

  render() {

    const listaMes = [
        {label: 'Selecione...', value: ''},
        {label: 'Janeiro', value: '1'},
        {label: 'Fevereiro', value: '2'},
        {label: 'Março', value: '3'},
        {label: 'Abril', value: '4'},
        {label: 'Maio', value: '5'},
        {label: 'Junho', value: '6'},
        {label: 'Julho', value: '7'},
        {label: 'Agosto', value: '8'},
        {label: 'Setembro', value: '9'},
        {label: 'Outubro', value: '10'},
        {label: 'Novembro', value: '11'},
        {label: 'Dezembro', value: '12'}
    ]

    const tipoLancamento = [
        {label: 'Selecione...', value: ''},
        {label: 'RECEITA', value: '1'},
        {label: 'DESPESA', value: '2'}
    ]

    return (
      <div className="container">
        <Card title="Consulta Lancamentos">
          <div className="row">
            <div className="col-lg-6">
              <div className="bs-component">

                <FormGroup label="Ano" htmlFor="inputAno">
                  <input
                    type="text"
                    class="form-control"
                    id="inputAno"
                    placeholder="Digite o Ano"
                    value={this.state.ano}
                    onChange={(e) => this.setState({ano: e.target.value})}
                  />
                </FormGroup>
               
                <FormGroup label="Mes" >
                    <SelectMenu lista={listaMes}  className="inputMes"
                     type="text"
                     class="form-control"
                     id="inputMes"
                     placeholder="Digite o Mes"
                     value={this.state.mes}
                     onChange={(e) => this.setState({mes: e.target.value})}/>
                </FormGroup>
                
                <FormGroup label="Tipo Lancamento" >
                    <SelectMenu lista={tipoLancamento}  
                                className="form-control"
                                id="inputTipoLancamento"
                                value={this.state.tipo}
                                onChange={(e) => this.setState({tipo: e.target.value})}/>
                </FormGroup>

                <button type="button" class="btn btn-success" onClick={this.buscar}>Buscar</button>
                <button type="button" class="btn btn-danger">Cadastrar</button>

              </div>
            </div>
          </div>
        
        </Card>
        <div className="row">
            <div className="col-md-12"> 
                <div className="bs-component">
                    <LancamentosTable lancamentos={this.state.lancamentos}/>
                </div>
            </div>
        </div>


      </div>
    );
  }
}

export default withRouter(ConsultaLancamentos);
