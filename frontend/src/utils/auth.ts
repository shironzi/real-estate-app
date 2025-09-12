import api from "./axios";

/**
 * Login Request
 * --------------------
 * @param email 
 * @param password 
 * @returns {number} HTTP status code (e.g., 200 on success)
 * 
 * - On Success, Stores the JWT token to local storage.
 * - On Error, Returns the status code and should handle by the caller.
 */

export async function login(email: string, password: string) {
    const res = await api.post("/auth/login", { email, password });

    if (res.status === 200) {
        const data = res.data;
        localStorage.setItem("token", data.token);
    }

    return res.status;
}
