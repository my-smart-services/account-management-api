package contracts.user.get

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name "Find all user"
    description """
    Represents the search for an existing user by his name:

    '''
    given:
        short username
    when:
        search user by username
    then:
        all user
    '''
    """

    request {
        url "/api/user"
        method GET()
    }

    response {
        status BAD_REQUEST()
        headers {
            contentType applicationJson()
        }
        body(
                [

                        [
                                username : "MaxMust",
                                email    : "max.mustermann@example.org",
                                firstname: "Max",
                                lastname : "Mustermann"
                        ],
                        [
                                username : "MaxMusthaus",
                                email    : "max.musterhaus@example.org",
                                firstname: "Maxi",
                                lastname : "Musterhaus",
                        ],
                        [
                                username : "Max",
                                email    : "max.muster@example.org",
                                firstname: "Maxi",
                                lastname : "Musterhaus"
                        ],
                        [
                                username : "Max02",
                                email    : any(),
                                firstname: any(),
                                lastname : "Musterhaus"
                        ]
                ]
        )
    }
}
