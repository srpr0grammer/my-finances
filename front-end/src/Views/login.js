import React from 'react';
import Card from './../Components/card';
import FormGroup from './../Components/form-group';
import { withRouter } from 'react-router-dom'
import UsuarioService from './../app/service/usuarioService';
import LocalStorageService from './../app/service/localStorageService';
import { mensagemErro } from '../Components/toastr';

class Login extends React.Component {
    
    state = {
        email : '',
        senha: ''
    }

    constructor(){
        super()
        this.service = new UsuarioService();
    }
    
    //funcao para logar
    entrar = () => {
        // realizando a requisicao no back-end
        this.service.autenticar( {
            email: this.state.email,
            senha: this.state.senha
        }).then( response=> {
            //adicionando e recuperando o usuario logado na sessao.(LOCAL STORAGE)
            LocalStorageService.adicionarItem('_usuario_logado', response.data)
            
            //Sem ser refatorado
            //localStorage.setItem('_usuario_logado', JSON.stringify(response.data))

            this.props.history.push('/home')
        }).catch( error => {
            mensagemErro(error.response.data)
        })
        
      //  console.log('Email: ', this.state.email);
      //  console.log('Senha: ', this.state.senha);
    }

    //funcao para acesssar a tela CADASTRO DE USUARIO quando clicar em CADASTRAR
    telaCadastroUsuario = () =>{
        this.props.history.push('/cadastro-usuarios')
    }

    render(){

        return (
        <div className="container">
            <div className="row">
                <div className="col-md-6" style={ { position: 'relative', left: '300px' } }>
                    <div className="bs-docs-section">
                        <Card title="Login">
                            <div className="row">
                                <div className="col-lg-12">
                                    <div className="bs-component">
                                        <fieldset>

                                            <FormGroup label="Email: *" htmlFor="exampleInputEmail1">
                                            <input onChange={e => this.setState({email: e.target.value})} 
                                                   value={this.state.email} type="email" className="form-control" 
                                                   id="exampleInputEmail1" 
                                                   aria-describedby="emailHelp" 
                                                   placeholder="Digite o Email"></input>
                                            </FormGroup>

                                            <FormGroup label="Senha: *" htmlFor="exampleInputPassword1">
                                            <input onChange={e => this.setState({senha : e.target.value})} 
                                                   value={this.state.senha} 
                                                   type="password" className="form-control" 
                                                   id="exampleInputPassword1" placeholder="Password"/>

                                            </FormGroup>
                                            
                                            <button onClick={ this.entrar} className="btn btn-success">Entrar</button>
                                            <button onClick= { this.telaCadastroUsuario } className="btn btn-danger">Cadastrar</button>

  
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        </Card>
                    </div>
                </div>
            </div>
        </div>
        )
    }
}

export default withRouter ( Login )
