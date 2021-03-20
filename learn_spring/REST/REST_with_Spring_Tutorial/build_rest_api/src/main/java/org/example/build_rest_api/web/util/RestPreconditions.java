package org.example.build_rest_api.web.util;

import org.example.build_rest_api.web.exception.ResourceNotFoundException;

public class RestPreconditions {
    
    private RestPreconditions() {
        throw new AssertionError();
    }

    public static void checkFound(final boolean expression) {
        if (!expression) {
            throw new ResourceNotFoundException();
        }
    }

    public static <T> T checkFound(final T resource) {
        if (resource == null) {
            throw new ResourceNotFoundException();
        }
        return resource;
    }
}
