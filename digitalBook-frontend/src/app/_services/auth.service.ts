import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const AUTH_API = 'http://localhost:8081/api/auth/';
// const AUTH_API ='https://c6en53f5tf.execute-api.us-east-1.amazonaws.com/UAT/';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json', 
  'Access-Control-Allow-Origin' : '*'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(private http: HttpClient) { }

  login(username: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signin', {
      username,
      password
    }, httpOptions);
  }

  register(username: string, email: string, role: string, password: string): Observable<any> {
    return this.http.post(AUTH_API + 'signup', {
      username,
      email,
      role,
      password
    }, httpOptions);
  }
}
