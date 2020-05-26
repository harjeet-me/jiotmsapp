import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'company-profile',
        loadChildren: () => import('./company-profile/company-profile.module').then(m => m.JiotmsappCompanyProfileModule),
      },
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.JiotmsappCustomerModule),
      },
      {
        path: 'trip',
        loadChildren: () => import('./trip/trip.module').then(m => m.JiotmsappTripModule),
      },
      {
        path: 'invoice',
        loadChildren: () => import('./invoice/invoice.module').then(m => m.JiotmsappInvoiceModule),
      },
      {
        path: 'payment',
        loadChildren: () => import('./payment/payment.module').then(m => m.JiotmsappPaymentModule),
      },
      {
        path: 'invoice-report',
        loadChildren: () => import('./invoice-report/invoice-report.module').then(m => m.JiotmsappInvoiceReportModule),
      },
      {
        path: 'invoice-item',
        loadChildren: () => import('./invoice-item/invoice-item.module').then(m => m.JiotmsappInvoiceItemModule),
      },
      {
        path: 'product-item',
        loadChildren: () => import('./product-item/product-item.module').then(m => m.JiotmsappProductItemModule),
      },
      {
        path: 'accounts',
        loadChildren: () => import('./accounts/accounts.module').then(m => m.JiotmsappAccountsModule),
      },
      {
        path: 'transactions-record',
        loadChildren: () => import('./transactions-record/transactions-record.module').then(m => m.JiotmsappTransactionsRecordModule),
      },
      {
        path: 'container',
        loadChildren: () => import('./container/container.module').then(m => m.JiotmsappContainerModule),
      },
      {
        path: 'equipment',
        loadChildren: () => import('./equipment/equipment.module').then(m => m.JiotmsappEquipmentModule),
      },
      {
        path: 'insurance',
        loadChildren: () => import('./insurance/insurance.module').then(m => m.JiotmsappInsuranceModule),
      },
      {
        path: 'contact',
        loadChildren: () => import('./contact/contact.module').then(m => m.JiotmsappContactModule),
      },
      {
        path: 'driver',
        loadChildren: () => import('./driver/driver.module').then(m => m.JiotmsappDriverModule),
      },
      {
        path: 'carrier',
        loadChildren: () => import('./carrier/carrier.module').then(m => m.JiotmsappCarrierModule),
      },
      {
        path: 'location',
        loadChildren: () => import('./location/location.module').then(m => m.JiotmsappLocationModule),
      },
      {
        path: 'email',
        loadChildren: () => import('./email/email.module').then(m => m.JiotmsappEmailModule),
      },
      {
        path: 'invoice-history',
        loadChildren: () => import('./invoice-history/invoice-history.module').then(m => m.JiotmsappInvoiceHistoryModule),
      },
      {
        path: 'account-history',
        loadChildren: () => import('./account-history/account-history.module').then(m => m.JiotmsappAccountHistoryModule),
      },
      {
        path: 'report',
        loadChildren: () => import('./report/report.module').then(m => m.JiotmsappReportModule),
      },
      {
        path: 'file-system',
        loadChildren: () => import('./file-system/file-system.module').then(m => m.JiotmsappFileSystemModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class JiotmsappEntityModule {}
