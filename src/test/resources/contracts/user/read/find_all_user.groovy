package contracts.user.read

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    priority(1)
    description """
    Represents the search for all users

    '''
    given:
        existing users
    when:
        search for all users
    then:
        all user
    '''
    """

    request {
        url "/api/user"
        method GET()
    }

    response {
        status OK()
        headers {
            contentType applicationJson()
        }
        body
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

    }
}
