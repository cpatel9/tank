/**
 * Created by tmichels on 8/3/14.
 */

var RestController = function($scope, $http, Restangular){

    var baseAccounts = Restangular.all('accounts');

    baseAccounts.getList().then(function(accounts) {
        $scope.allAccounts = accounts;
    });

    $scope.accounts = Restangular.all('accounts').getList().$object;


    var newAccount = {name: "Gonto's account"};

    // POST /accounts
    baseAccounts.post(newAccount);

    // GET to http://www.google.com/ You set the URL in this case
    Restangular.allUrl('googlers', 'http://www.google.com/').getList();


}