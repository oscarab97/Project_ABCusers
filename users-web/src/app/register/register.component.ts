import { Component } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
})
export class RegisterComponent {
  user:User= new User();
  constructor(private userService:UserService,private enrutador:Router){}

  onSubmit(){
    //console.log(this.user);
    this.guardarUser();
  }
  private guardarUser(){
    this.userService.userRegister(this.user).subscribe(
      {
        next:(lamd)=>{
          this.listarUsers();
        },error:(error:any)=>{console.log(error)}
      }
    )
  }

  listarUsers(){
    this.enrutador.navigate(['/Home']);
  }
  

}
