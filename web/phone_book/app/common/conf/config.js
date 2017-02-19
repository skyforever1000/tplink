app.config(function($routeProvider){
    $routeProvider
    .when('/',{
        templateUrl: 'app/home/view/index.html',
        controller: 'homeController'
    })
    .when('/contact', {
        templateUrl: 'app/home/view/contact.html',
        controller: 'contactController'
    })
    .when('/group', {
        templateUrl: 'app/home/view/group.html',
        controller: 'groupController'
    })
    .otherwise({redirectTo:'/'});;
})