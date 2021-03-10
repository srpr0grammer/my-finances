import React from "react";
import Card from "./../Components/card";
import FormGroup from "./../Components/form-group";
import { withRouter } from "react-router-dom";
import UsuarioService from "./../app/service/usuarioService";
import { mensagemErro, mensagemSucesso } from './../Components/toastr';

class CadastroUsuario extends React.Component {
  //state para cadastrar usuario
  state = {
    nome: "",
    email: "",
    senha: "",
    senhaRepeticao: "",
    mensagemErro: null,
  };

  constructor() {
    super();
    this.service = new UsuarioService();
  }

  //validando tela de cadastroUsuario
  validar() {
    const msg = []

    if (!this.state.nome) {
      msg.push('O campo nome é obrigatório.')
    }

    if (!this.state.email) {
      msg.push('O campo e-mail é obrigatório')
    }  else if ( !this.state.email.match(/^[a-z0-9.]+@[a-z0-9]+\.[a-z]/)) {
      msg.push.length('A expressão de e-mail é inválida! Por favor, digite uma expressão de e-mail válida.')
    } 

    if (!this.state.senha || !this.state.senhaRepeticao) {
      msg.push('O campo senha é obrigatório.')
    } else if (this.state.senha !== this.state.senhaRepeticao) {
      msg.push('As senhas estão divergentes.')
    } 

    return msg;
  }

  //fuincao para cadastrar usuario
  cadastrar = () => {

    const msg = this.validar();

    if (msg && msg.length > 0) {
      msg.forEach( (msg, index) => {
      mensagemErro(msg);
      });

      return false;
    }

    // realizando a requisicao no back-end
    this.service
      .cadastrar({
        nome: this.state.nome,
        email: this.state.email,
        senha: this.state.senha,
        senhaRepeticao: this.state.senhaRepeticao,
      })
      .then((response) => {
        mensagemSucesso('Uusario cadastrado com sucesso! Faça o login para acessar o sistema.')
        this.props.history.push('/login')
      })
      .catch((error) => {
        mensagemErro(error.response.data)
      });

    /* console.log('Nome: ', this.state.nome);
        console.log('Email: ', this.state.email);
        console.log('Senha: ', this.state.senha);
        console.log('Digite sua senha novamente: ', this.state.senhaRepeticao); */
  };

  //funcao para acessar a tela de login ao clicar em cancelar
  telaCancelar = () => {
    this.props.history.push("/login");
  };

  render() {
    return (
      <div className="container">
        <Card title="Cadastro de Usuario">
          <div className="row">
            <span>{this.state.mensagemError}</span>
          </div>
          <div className="row">
            <div className="col-lg-12">
              <div className="bs-component"></div>

              <FormGroup label="Nome: *" htmlFor="inputNome">
                <input
                  type="text"
                  className="form-control"
                  id="inputNome"
                  name="nome"
                  placeholder="Digite o Nome"
                  onChange={(e) => this.setState({ nome: e.target.value })}
                  value={this.state.nome}
                />
              </FormGroup>

              <FormGroup label="Email: *" htmlFor="inputEmail">
                <input
                  type="text"
                  className="form-control"
                  id="inputEmail"
                  name="email"
                  placeholder="Digite o Email"
                  onChange={(e) => this.setState({ email: e.target.value })}
                  value={this.state.email}
                />
              </FormGroup>

              <FormGroup label="Senha: *" htmlFor="inputSenha">
                <input
                  type="password"
                  className="form-control"
                  id="inputSenha"
                  name="email"
                  placeholder="Digite a Senha"
                  onChange={(e) => this.setState({ senha: e.target.value })}
                  value={this.state.senha}
                />
              </FormGroup>

              <FormGroup label="Senha: *" htmlFor="inputRepetirSenha">
                <input
                  type="password"
                  className="form-control"
                  id="inputRepetirSenha"
                  name="repetirSenha"
                  placeholder="Digite a Senha novamente"
                  onChange={(e) =>
                    this.setState({ senhaRepeticao: e.target.value })
                  }
                  value={this.state.senhaRepeticao}
                />
              </FormGroup>

              <button
                onClick={this.cadastrar}
                type="button"
                className="btn btn-success"
              >
                Salvar
              </button>
              <button
                onClick={this.telaCancelar}
                type="button"
                class="btn btn-danger"
              >
                Voltar
              </button>
            </div>
          </div>
        </Card>
      </div>
    );
  }
}

export default withRouter(CadastroUsuario);
