import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { User } from './user';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private url = "http://localhost:8080/api/v1/users";
  private url2 = "http://localhost:8080/api/v1/user";
  private url3 = "http://localhost:8080/api/v1/user/export";
  constructor(private clientHttp:HttpClient) { }
  
  userList():Observable<User[]>{
    return this.clientHttp.get<User[]>(this.url);
  }
  userRegister(user:User):Observable<object>{
    return this.clientHttp.post(this.url2,user);
  } 
  userDelete(id:string):Observable<object>{
     return this.clientHttp.delete(`${this.url2}/${id}`);
  }
  userExportExcel(){
     this.clientHttp.get(this.url3);

  }
}
