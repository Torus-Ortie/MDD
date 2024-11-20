export interface LoginRequest {
    emailOrName: string;
    password: string;
}

export interface RegisterRequest {
    name: string;
    email: string;
    password: string;
}

export interface AuthSuccess {
    token: string;
}