import React from "react";

import { Route, Switch, HashRouter } from 'react-router-dom'
import Login from './../Views/login';
import CadastroUsuario from './../Views/cadastroUsuario';

function Rotas() {
    return (
        <HashRouter>
            <Switch>
                <Route path="/login" component={Login} /> 
                <Route path="/cadastro-usuario" component={CadastroUsuario} /> 

            </Switch>
        </HashRouter>
    )
}

export default Rotas;
