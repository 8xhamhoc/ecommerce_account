import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITblUser } from '../tbl-user.model';

@Component({
  selector: 'jhi-tbl-user-detail',
  templateUrl: './tbl-user-detail.component.html',
})
export class TblUserDetailComponent implements OnInit {
  tblUser: ITblUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tblUser }) => {
      this.tblUser = tblUser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
