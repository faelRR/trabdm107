<?php 
    use \Psr\Http\Message\ServerRequestInterface as Request; 
    use \Psr\Http\Message\ResponseInterface as Response;

    require '../vendor/autoload.php';
    $config['displayErrorDetails'] = true;
    $config['addContentLengthHeader'] = false;

    // -- dados para conexão com o banco -- //    
    $config['db']['host'] = "localhost";
    $config['db']['user'] = "root";
    $config['db']['pass'] = "";
    $config['db']['dbname'] = "trabdm107";

    $app = new \Slim\App(["config" => $config]);    
    $username = $_SERVER["PHP_AUTH_USER"];
    $password = $_SERVER["PHP_AUTH_PW"];
    $container = $app->getContainer();

    $container['db'] = function ($c) {
        $dbConfig = $c['config']['db'];
        $pdo = new PDO("mysql:host=". $dbConfig['host'] . ";dbname=" . $dbConfig['dbname'], $dbConfig['user'], $dbConfig['pass']);
        $pdo->setAttribute(PDO::ATTR_ERRMODE,PDO::ERRMODE_EXCEPTION);
        $pdo->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE,PDO::FETCH_ASSOC);
        $db = new NotORM($pdo);
        return $db;
    };
    
    $usersInfo = $container['db']->users->where("userlogin = ?", $username)->where("userpassword = ?", $password);
    $res = array();
    $res["userlogin"] = "";
    $res["userpassword"] = "";

    foreach ($usersInfo as $userInfo) {
        $res["userlogin"] = $userInfo["userlogin"];
        $res["userpassword"] = $userInfo["userpassword"];
    }

    $app->add(new Tuupola\Middleware\HttpBasicAuthentication([
        "users" => [
            $res["userlogin"] => $res["userpassword"]
        ],
        "error" => function ($request, $response, $arguments) {
            $data["status"] = "error";
            $data["message"] = $arguments["message"];
            return $response
                ->withHeader("Content-Type", "application/json")
                ->write(json_encode($data, JSON_UNESCAPED_SLASHES | JSON_PRETTY_PRINT));
        }
    ]));

/*    $app->get('/api/entregas/{id}', function (Request $request, Response $response) {
        $id = $request->getAttribute('id');
        $entrega = $this->db->entregas("id=?", $id)->fetch();
        return $response->withJson($entrega);
    });

    $app->post('/api/entregas/', function (Request $request, Response $response) {
        $body = $request->getParsedBody();
        $entrega = array(
            "id"  =>  $body["id"],
            "numPedido" => $body["numPedido"],
            "idCliente" =>  $body["idCliente"],
            "nomeRecebedor" =>  $body["nomeRecebedor"],
            "cpfRecebedor" =>  $body["cpfRecebedor"],
            "dataHoraEntrega" => $body["dataHoraEntrega"]
        );
        $result = $this->db->entregas()->insert($entrega);
        return $result;
    });
*/
    $app->put('/api/entregas/{id}', function (Request $request, Response $response) {
        $id = $request->getAttribute('id');
        $body = $request->getParsedBody();
        $entrega = $this->db->entregas[$id];
        if ($entrega) {
            $data = array(
                "numPedido" => $body["numPedido"],
                "idCliente" =>  $body["idCliente"],
                "nomeRecebedor" =>  $body["nomeRecebedor"],
                "cpfRecebedor" =>  $body["cpfRecebedor"],
                "dataHoraEntrega" => $body["dataHoraEntrega"]
            );
            $result = $entrega->update($data);
            
        }else{
            return "Pedido não encontrado";
        }
        
    });

    $app->delete('/api/entregas/{id}', function(Request $request, Response $response){
        $idEntrega = $request->getAttribute("id");
        if ($entrega = $this->db->entregas("id = ?", $idEntrega)->fetch()){
            if ($entrega->fetch()) {
                $deleted = $entrega->delete();
                return $entrega;
            }
        } else {
            return "Pedido não encontrado";
        }
        
    });        

    $app->run(); 
    
?>