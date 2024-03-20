import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITblRole } from '../tbl-role.model';

@Component({
  selector: 'jhi-tbl-role-detail',
  templateUrl: './tbl-role-detail.component.html',
})
export class TblRoleDetailComponent implements OnInit {
  tblRole: ITblRole | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tblRole }) => {
      this.tblRole = tblRole;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
