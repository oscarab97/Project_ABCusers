import { Component } from '@angular/core';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
})
export class ListComponent {
  userList?:User[];
  constructor(private UserService:UserService){}
  ngOnInit(){
    this.getAll();
  }
  private getAll(){
    this.UserService.userList().subscribe(
      (lamb =>{
        this.userList = lamb;
      })
    )
  }
  onDelete(){
    const modalDiv= document.getElementById("modalRegister");
    //console.log(modalDiv);

    let id = modalDiv!.getAttribute('name') as string;
    this.UserService.userDelete(id).subscribe(
      {
        next:(lamd)=>{
          this.getAll();
        },error:(error:any)=>{console.log(error)}
      }
    )
    this.closeModal();
  }
  openModal(id:Number){
    const modalDiv= document.getElementById("modalRegister");
    modalDiv?.setAttribute('name',id.toString());
    if(modalDiv !=null){
      modalDiv.style.display ='block'
    }
  }
  closeModal(){
    const modalDiv= document.getElementById("modalRegister");
    if(modalDiv !=null){
      modalDiv.style.display ='none'
    }
  }
  onExport(){
    this.UserService.userExportExcel().subscribe(
      {
        next:(lamd)=>{
          this.getAll();
        },error:(error:any)=>{console.log(error)}
      }
    );
  }
}
