import React from "react";
import Card from "./../Components/card";
import FormGroup from "./../Components/form-group";

class CadastroUsuario extends React.Component {
  
    //state para cadastrar usuario
    state = {
        nome: '',
        email: '',
        senha: '',
        senhaRepeticao: ''
    }

    //fuincao para cadastrar usuario
    cadastrar = () => {
        console.log('Nome: ', this.state.nome);
        console.log('Email: ', this.state.email);
        console.log('Senha: ', this.state.senha);
        console.log('Digite sua senha novamente: ', this.state.senhaRepeticao);
    }

  render() {
    return (
      <div className="container">
        <Card title="Cadastro de Usuario">
          <div className="row">
            <div className="col-lg-12">
              <div className="bs-component"></div>

              <FormGroup label="Nome: *" htmlFor="inputNome">
                 <input type="text" 
                        className="form-control" 
                        id="inputNome" 
                        name="nome" 
                        placeholder="Digite o Nome"
                        onChange={e => this.setState({nome: e.target.value})}
                        va={this.state.nome}/>
              </FormGroup>
             
              <FormGroup label="Email: *" htmlFor="inputEmail">
                 <input type="text" 
                        className="form-control" 
                        id="inputEmail" 
                        name="email" 
                        placeholder="Digite o Email"
                        onChange={e => this.setState({email: e.target.value})}
                        value={this.state.email}/>
              </FormGroup>
             
              <FormGroup label="Senha: *" htmlFor="inputSenha">
                 <input type="password" 
                        className="form-control" 
                        id="inputSenha" 
                        name="email" 
                        placeholder="Digite a Senha"
                        onChange={e => this.setState({senha: e.target.value})}
                        value={this.state.senha}/>
              </FormGroup>
             
              <FormGroup label="Senha: *" htmlFor="inputRepetirSenha">
                 <input type="password" 
                        className="form-control" 
                        id="inputRepetirSenha" 
                        name="repetirSenha" 
                        placeholder="Digite a Senha novamente"
                        onChange={e => this.setState({senhaRepeticao: e.target.value})}
                        value={this.state.senhaRepeticao}/>
              </FormGroup>

              <button onClick={this.cadastrar} type="button" className="btn btn-success">Salvar</button>
              <button type="button" class="btn btn-danger">Voltar</button>

            </div>
          </div>
        </Card>
      </div>
    );
  }
}

export default CadastroUsuario;
