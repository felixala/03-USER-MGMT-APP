package com.felixlaura.bindings;

import lombok.Data;

@Data
public class ActiveAccount {

    private String email;

    private String tempPwd;

    private String newPwd;

    private String confirmPwd;
}
