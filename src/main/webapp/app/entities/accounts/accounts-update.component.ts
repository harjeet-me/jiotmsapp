import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAccounts, Accounts } from 'app/shared/model/accounts.model';
import { AccountsService } from './accounts.service';

@Component({
  selector: 'jhi-accounts-update',
  templateUrl: './accounts-update.component.html',
})
export class AccountsUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    balance: [],
    over30: [],
    over60: [],
    over90: [],
    createdOn: [],
    createdBy: [],
    updatedOn: [],
    updatedBy: [],
  });

  constructor(protected accountsService: AccountsService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accounts }) => {
      if (!accounts.id) {
        const today = moment().startOf('day');
        accounts.createdOn = today;
        accounts.updatedOn = today;
      }

      this.updateForm(accounts);
    });
  }

  updateForm(accounts: IAccounts): void {
    this.editForm.patchValue({
      id: accounts.id,
      balance: accounts.balance,
      over30: accounts.over30,
      over60: accounts.over60,
      over90: accounts.over90,
      createdOn: accounts.createdOn ? accounts.createdOn.format(DATE_TIME_FORMAT) : null,
      createdBy: accounts.createdBy,
      updatedOn: accounts.updatedOn ? accounts.updatedOn.format(DATE_TIME_FORMAT) : null,
      updatedBy: accounts.updatedBy,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accounts = this.createFromForm();
    if (accounts.id !== undefined) {
      this.subscribeToSaveResponse(this.accountsService.update(accounts));
    } else {
      this.subscribeToSaveResponse(this.accountsService.create(accounts));
    }
  }

  private createFromForm(): IAccounts {
    return {
      ...new Accounts(),
      id: this.editForm.get(['id'])!.value,
      balance: this.editForm.get(['balance'])!.value,
      over30: this.editForm.get(['over30'])!.value,
      over60: this.editForm.get(['over60'])!.value,
      over90: this.editForm.get(['over90'])!.value,
      createdOn: this.editForm.get(['createdOn'])!.value ? moment(this.editForm.get(['createdOn'])!.value, DATE_TIME_FORMAT) : undefined,
      createdBy: this.editForm.get(['createdBy'])!.value,
      updatedOn: this.editForm.get(['updatedOn'])!.value ? moment(this.editForm.get(['updatedOn'])!.value, DATE_TIME_FORMAT) : undefined,
      updatedBy: this.editForm.get(['updatedBy'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccounts>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
