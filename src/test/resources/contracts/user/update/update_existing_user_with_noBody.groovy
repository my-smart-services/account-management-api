package contracts.user.createUser

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(3)
    description """
    Represents an update without new fields of a user:

    '''
    given:
        the username is valid
    when:
        a user is updated
    then:
        accepted nothing to update
    '''
    """

    request {
        url '/api/user/MaxMust'
        method PUT()
        headers {
            contentType applicationJson()
        }
        body("""{}""")
    }

    response {
        status ACCEPTED()
        headers {
            contentType applicationJson()
        }
    }
}
