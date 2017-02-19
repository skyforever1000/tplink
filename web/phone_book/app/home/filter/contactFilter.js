app.filter("getContactObj", function(Contact){
    return function(input) {
        var result = '';
        const contacts = Contact.getAll();

        for (let i = 0; i < contacts.length; i++){
            if (contacts[i].id == input) {
                result = contacts[i] || {};
                break;
            }
        }

        return result;
    }
});

app.filter("getContactFullName", function(Contact){
    return function(input) {
        var result = '';
        const contacts = Contact.getAll();

        for (let i = 0; i < contacts.length; i++){
            if (contacts[i].id == input) {
                result = (contacts[i].firstName + " " + contacts[i].lastName) || 'Unknown';
                break;
            }
        }
        
        return result || 'Not Found';
    }
});

app.filter("getContactAddress", function(Contact){
    return function(input) {
        const contacts = Contact.getAll();
        var result = '';

        for (let i = 0; i < contacts.length; i++){
            if (contacts[i].id == input) {
                result = contacts[i].address || '';
                break;
            }
        }

        return result || 'Not Found';
    }
});

app.filter("getContactPhone", function(Contact){
    return function(input) {
        const contacts = Contact.getAll();
        var result = '';

        for (let i = 0; i < contacts.length; i++){
            if (contacts[i].id == input) {
                result = contacts[i].phone || '';
                break;
            }
        }

        return result || 'Not Found';
    }
});