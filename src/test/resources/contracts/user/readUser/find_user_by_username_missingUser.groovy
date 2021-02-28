package contracts.user.get

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "Find no user by username"
    description """
    Represents the search for an existing user by his name:

    '''
    given:
        user for username not registered
    when:
        search user by username
    then:
        return error
    '''
    """

    request {
        url "/api/user/NotAnExistingUser"
        method GET()
    }

    response {
        status NOT_FOUND()
    }
}
